package rs.pingvin.d12.model;

public class Profession {

    private String name;
    private double coefficient;

    public Profession(String name, double coefficient) {
        this.name = name;
        this.coefficient = coefficient;
    }

    public String getName() {
        return name;
    }

    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof Profession))
            return false;

        Profession profession = (Profession) obj;
        return name.equals(profession.getName());
    }

}
