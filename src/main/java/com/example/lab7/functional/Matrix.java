package com.example.lab7.functional;

public class Matrix {

    private final Integer[][] matrix;

    public Matrix(String payload) throws IllegalArgumentException, NumberFormatException {

        String[] rows = payload.split("\n");
        int size = rows.length;
        this.matrix = new Integer[size][size];
        for (int i = 0; i < rows.length; i++) {
            String[] element = rows[i].split(" ");
            if (rows.length != element.length)
                throw new IllegalArgumentException("Количество строк не совпадает с количеством столбцов");
            for (int j = 0; j < element.length; j++) {
                if (element[j].equals("0") || element[j].equals("1")) {
                    matrix[i][j] = Integer.parseInt(element[j]);
                } else {
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
