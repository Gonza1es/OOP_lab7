package com.example.lab7.functional;

import java.util.Arrays;

public class Matrix {

    private final Integer[][] matrix;

    public Matrix(String payload) throws IllegalArgumentException {

        String[] rows = payload.split("\n");
        int columnCount = rows[0].split(" ").length;
        this.matrix = new Integer[rows.length][columnCount];
        for (int i = 0; i < rows.length; i++) {
            String[] element = rows[i].split(" ");
            for (int j = 0; j < element.length; j++) {
                try {
                    if (Integer.parseInt(element[j]) > rows.length) {
                        int k = i+1;
                        int s = j+1;
                        throw new IllegalArgumentException("Значение в ячейке [" +
                                k + "][" + s + "] не может быть больше " + rows.length);
                    }
                    matrix[i][j] = Integer.parseInt(element[j]);
                } catch (NumberFormatException e) {
                    int k = i+1;
                    int s = j+1;
                    throw new IllegalArgumentException("Неверный формат данных в ячейке [" +
                            k + "][" + s + "]");
                }
            }
        }
    }

    public Integer[][] getMatrix() {
        return matrix;
    }
}
