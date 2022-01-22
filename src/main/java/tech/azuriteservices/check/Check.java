package tech.azuriteservices.check;

public abstract class Check {

    public abstract String getCheckName();

    public abstract String getTypeName();

    public abstract int getMaxVL();

    public abstract int getVL();
}
