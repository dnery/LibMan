package br.usp.icmc.ssc0103;

class Test{
    private String string;
    private boolean bool;
    private int integer1;

    public Test(String string, boolean bool, int integer1)
    {
        this.string = string;
        this.bool = bool;
        this.integer1 = integer1;
    }

    public String getString()
    {
        return string;
    }

    public boolean isBool()
    {
        return bool;
    }

    public int getInteger1()
    {
        return integer1;
    }
}

public class Main
{
    static Shell shell;

    public static void main(String[] args)
    {
        //shell = new Shell();
        //shell.runCommand();

        Test test = new Test(args[0], Boolean.parseBoolean(args[2]), Integer.parseInt(args[1]));

        System.out.println(test.hashCode());
    }
}

