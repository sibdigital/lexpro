package ru.sibdigital.lexpro.model.enums;

public enum RkkStatuses {
    IN_WORK(1),           // В РАБОТЕ
    WITHDRAW(2),          // ОТОЗВАН
    FOR_CONSIDERATION(3), // К РАССМОТРЕНИЮ
    DECLINED(4),          // ОТКЛОНЕН
    ACCEPTED(5);          //ПРИНЯТ

    private final int value;
    private RkkStatuses(int value) { this.value = value; }
    public int getValue() { return value; }
}
