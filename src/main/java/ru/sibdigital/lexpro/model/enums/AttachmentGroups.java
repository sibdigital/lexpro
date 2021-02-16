package ru.sibdigital.lexpro.model.enums;

public enum AttachmentGroups {
    INTRODUCED(1), // ВНЕС
    ACCEPTED(2),   // ПРИНЯТ
    TO_SESSION(3), // К ЗАСЕД
    RECOMMENDED(4),// РЕК
    REQUIRED(5),   // НЕОБ
    VIZA(6);       // ВИЗ

    private final int value;
    private AttachmentGroups(int value) { this.value = value; }
    public int getValue() { return value; }
}
