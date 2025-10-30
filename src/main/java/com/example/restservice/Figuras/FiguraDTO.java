package com.example.restservice.Figuras;

public class FiguraDTO {
    private String nombre;
    private double area;
    private double perimetro;

    public FiguraDTO(Figura f) {
        this.nombre = f.toString();
        this.area = f.calcularArea();
        this.perimetro = f.calcularPerimetro();
    }

    public String getNombre() { return nombre; }
    public double getArea() { return area; }
    public double getPerimetro() { return perimetro; }
}


