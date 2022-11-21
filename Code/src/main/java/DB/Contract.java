package main.java.DB;

import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Contract {
    public int id;
    public int offerId;
    public String finalStatus;
    public double agreedRate;
    private final static DBLoader db = DBLoader.getInstance();

    public Contract(int id, int offerId, String finalStatus, double agreedRate) {
        this.id = id;
        this.offerId = offerId;
        this.finalStatus = finalStatus;
        this.agreedRate = agreedRate;
    }

    public static Contract getContractWithId(int id) {
        // Tested - Works
        Contract contract = null;
        try {
            String query = "SELECT * from contract WHERE contractId=?";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            contract = sqlSchemaToContract(res);
        } catch(Exception e) {
            System.out.println(e);
        }
        return contract;
    }

    public static Contract getContractWithOfferId(int offerId) {
        // Tested - Works
        Contract contract = null;
        try {
            String query = "SELECT * from contract WHERE offerId=?";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setInt(1, offerId);
            ResultSet res = statement.executeQuery();
            contract = sqlSchemaToContract(res);
        } catch(Exception e) {
            System.out.println(e);
        }
        return contract;
    }

    public static String addNewContract(JSONObject req) {
        try {
            String query = "INSERT into contract (contractId, offerId, finalStatus, agreedRate) " +
                    "values (null, ?, ?, ?)";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setInt(1, req.getInt("offerId"));
            statement.setString(2, req.getString("finalStatus"));
            statement.setDouble(3, req.getDouble("agreedRate"));
            statement.execute();
        } catch(Exception e) {
            System.out.println(e);
            return "Failed";
        }
        return "Success";
    }

    public static Contract sqlSchemaToContract(ResultSet res) throws SQLException {
        // Tested - Works
        if (res != null) {
            res.next();
            int contractId = res.getInt("contractId");
            int offerId = res.getInt("offerId");
            String finalStatus = res.getString("finalStatus");
            double agreedRate = res.getDouble("agreedRate");
            return new Contract(contractId, offerId, finalStatus, agreedRate);
        }
        return null;
    }

    public static void main(String[] args) {
        Contract test1 = Contract.getContractWithId(1);
        System.out.println(test1.agreedRate);
        Contract test2 = Contract.getContractWithOfferId(1);
        System.out.println(test1.finalStatus);

        JSONObject testObject = new JSONObject();
        testObject.put("offerId", 1);
        testObject.put("finalStatus", "Signed");
        testObject.put("agreedRate", 953.31);
        System.out.println(Contract.addNewContract(testObject));
    }
}
