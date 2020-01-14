package se.ecutb;


import se.ecutb.data.DataStorage;

public class App
{

    private static DataStorage dataStorage;

    static {
        dataStorage = DataStorage.INSTANCE;
    }

    public static void main( String[] args )
    {


    }
}
