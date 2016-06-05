package Server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import SharedData.*;

public class Database {

	private String dbfileName = "savedgame.db";

	/**
	 * 
	 * @param game
	 */
	public boolean saveGameData(GameData game) {
		try {
            FileOutputStream fos = new FileOutputStream(dbfileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(game);
            oos.close();
        }
        catch (Exception ex)
        {
            return false;
        }
        return true;
	}

	public GameData loadGameData() {
        try
        {
            FileInputStream fis = new FileInputStream(dbfileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            GameData game = (GameData) ois.readObject();
            ois.close();
            return game;
        }
        catch (Exception ex)
        {
            return null;
        }
   	}


}
