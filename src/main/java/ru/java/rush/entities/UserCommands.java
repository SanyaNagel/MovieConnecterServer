package ru.java.rush.entities;

public enum UserCommands {
    STOP("Остановка"), PLAY("Запуск"), SET_HASH("Кидай хэш"),
    WAITING_FOR_SYNCHRONIZATION("Ожидаем синхронизации");

    UserCommands(String com){
        this.com = com;
    }

    public String com;
}
