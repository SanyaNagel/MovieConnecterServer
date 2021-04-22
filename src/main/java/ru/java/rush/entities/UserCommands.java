package ru.java.rush.entities;

public enum UserCommands {
    STOP("Остановка"), PLAY("Запуск"), SET_HASH("Кидай хэш"),
    WAITING_FOR_SYNCHRONIZATION("Ожидаем синхронизации"),
    WAITING_EVERYONE_READY("Ожидаем готовности всех");
    UserCommands(String com){
        this.com = com;
    }

    public final String com;
}
