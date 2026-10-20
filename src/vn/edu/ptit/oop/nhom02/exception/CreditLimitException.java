package vn.edu.ptit.oop.nhom02.exception;

public class CreditLimitException extends RegistrationException {
    private static final long serialVersionUID = 1L;

    private final int currentCredits;
    private final int addedCredits;
    private final int maxCredits;

    public CreditLimitException(int currentCredits, int addedCredits, int maxCredits) {
        super(String.format("Vượt quá số tín chỉ tối đa! Đang có: %d TC, đăng ký thêm: %d TC, tổng: %d TC (Giới hạn tối đa là %d TC).",
              currentCredits, addedCredits, (currentCredits + addedCredits), maxCredits));
        this.currentCredits = currentCredits;
        this.addedCredits = addedCredits;
        this.maxCredits = maxCredits;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public int getAddedCredits() {
        return addedCredits;
    }

    public int getMaxCredits() {
        return maxCredits;
    }
}
