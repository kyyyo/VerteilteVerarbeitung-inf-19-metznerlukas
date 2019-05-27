package reactive_system.ClientSide;

import org.json.JSONObject;
import reactive_system.Enums.Input;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class RandomDataGenerator {

    private final DataOutputStream outputStream;
    private final Input sensorType;
    private final String[] randomIDs;
    private final Random randomGen;

    public RandomDataGenerator(DataOutputStream outputStream, Input sensorType, String[] randomIDs) {
        this.outputStream = outputStream;
        this.sensorType = sensorType;
        this.randomIDs = randomIDs;
        this.randomGen = new Random();
    }

    public void sendNewData() throws IOException {
        JSONObject testObj = new JSONObject();
        testObj.put("id", randomIDs[randomGen.nextInt(7)]);
        testObj.put("input", sensorType);
        outputStream.writeUTF(testObj.toString());
        outputStream.flush();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
