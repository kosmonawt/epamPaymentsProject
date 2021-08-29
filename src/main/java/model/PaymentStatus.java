package model;

enum PaymentStatus {

    NEW("New"),
    IN_PROGRESS("In progress"),
    DONE("Done");

    public final String status;

    PaymentStatus(String status) {
        this.status = status;
    }
}
