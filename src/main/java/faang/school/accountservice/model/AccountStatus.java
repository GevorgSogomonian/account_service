package faang.school.accountservice.model;

public enum AccountStatus {
    CURRENT("current"),
    FROZEN("frozen"),
    CLOSED("closed"),
    ;
    private final String status;

    AccountStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
