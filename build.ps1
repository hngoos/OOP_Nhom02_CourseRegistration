$ErrorActionPreference = 'Stop'
$projectRoot = $PSScriptRoot
$jarCommand = Get-Command jar -ErrorAction SilentlyContinue
if ($jarCommand) {
    $jarExecutable = $jarCommand.Source
}
else {
    # Oracle's Windows PATH shortcuts can expose javac without exposing jar.
    $settings = New-Object System.Diagnostics.ProcessStartInfo
    $settings.FileName = (Get-Command java -ErrorAction Stop).Source
    $settings.Arguments = '-XshowSettings:properties -version'
    $settings.UseShellExecute = $false
    $settings.CreateNoWindow = $true
    $settings.RedirectStandardError = $true
    $process = [Diagnostics.Process]::Start($settings)
    $properties = $process.StandardError.ReadToEnd()
    $process.WaitForExit()
    $jdkRootMatch = [regex]::Match($properties, '(?m)^\s*java\.home = (.+?)\r?$')
    if (-not $jdkRootMatch.Success) { throw 'Cannot locate the JDK. Install JDK 17 or newer.' }
    $jarExecutable = Join-Path $jdkRootMatch.Groups[1].Value.Trim() 'bin/jar.exe'
    if (-not (Test-Path -LiteralPath $jarExecutable)) { throw 'Cannot find jar.exe. Use a full JDK 17 or newer.' }
}
$tempRoot = [IO.Path]::GetFullPath([IO.Path]::GetTempPath())
$buildRoot = Join-Path $tempRoot ('oop-build-' + [guid]::NewGuid().ToString('N'))
New-Item -ItemType Directory -Path $buildRoot | Out-Null

try {
    $classes = Join-Path $buildRoot 'classes'
    New-Item -ItemType Directory -Path $classes | Out-Null
    $sourceList = Join-Path $buildRoot 'sources.txt'
    $sources = @(Get-ChildItem -LiteralPath (Join-Path $projectRoot 'src') -Recurse -File -Filter '*.java' |
        Sort-Object FullName | ForEach-Object { '"' + $_.FullName.Replace('\', '/') + '"' })
    [IO.File]::WriteAllLines($sourceList, $sources, [Text.UTF8Encoding]::new($false))

    & javac --release 17 -encoding UTF-8 -cp (Join-Path $projectRoot 'lib/jfreechart-1.5.4.jar') -d $classes ('@' + $sourceList)
    if ($LASTEXITCODE -ne 0) { throw 'Java compilation failed.' }

    $manifest = Join-Path $buildRoot 'MANIFEST.MF'
    [IO.File]::WriteAllText($manifest, "Class-Path: lib/jfreechart-1.5.4.jar`r`n", [Text.Encoding]::ASCII)
    $outputJar = Join-Path $buildRoot 'OOP_Nhom02.jar'
    & $jarExecutable --create --file $outputJar --main-class vn.edu.ptit.oop.nhom02.Main --manifest $manifest -C $classes . -C $projectRoot resources
    if ($LASTEXITCODE -ne 0) { throw 'JAR packaging failed.' }

    Move-Item -LiteralPath $outputJar -Destination (Join-Path $projectRoot 'OOP_Nhom02.jar') -Force
    Write-Output 'Built OOP_Nhom02.jar for Java 17 with the JFreeChart manifest entry.'
}
finally {
    $resolvedBuildRoot = [IO.Path]::GetFullPath($buildRoot)
    $tempPrefix = $tempRoot.TrimEnd('\', '/') + [IO.Path]::DirectorySeparatorChar
    if ($resolvedBuildRoot.StartsWith($tempPrefix, [StringComparison]::OrdinalIgnoreCase) -and
        [IO.Path]::GetFileName($resolvedBuildRoot).StartsWith('oop-build-')) {
        Remove-Item -LiteralPath $resolvedBuildRoot -Recurse -Force
    }
}
