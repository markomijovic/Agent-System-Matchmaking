package main.java.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Offer {
    public int id;
    public double offeredRate;
    public String offerStatus;
    public String clientUsername;
    public String providerUsername;
    private final static DBLoader db = DBLoader.getInstance();

    public Offer(int id, double rate, String status, String clientUsername, String providerUsername) {
        this.id = id;
        this.offerStatus = status;
        this.offeredRate = rate;
        this.clientUsername = clientUsername;
        this.providerUsername = providerUsername;

    }

    public static Offer getOfferWithId(int id) {
        // Tested - Works
        Offer offer = null;
        try {
            String query = "SELECT * from offer WHERE offerId=?";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            offer = sqlSchemaToOffer(res);
        } catch(Exception e) {
            System.out.println(e);
        }
        return offer;
    }

    public static Offer getOfferWithClientAndProvider(String clientUsername, String providerUsername) {
        // Tested - Works
        Offer offer = null;
        try {
            String query = "SELECT * from offer WHERE providerId=? and clientId=?";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setString(1, providerUsername);
            statement.setString(2, clientUsername);
            ResultSet res = statement.executeQuery();
            offer = sqlSchemaToOffer(res);
        } catch(Exception e) {
            System.out.println(e);
        }
        return offer;
    }

    public static Offer sqlSchemaToOffer(ResultSet res) throws SQLException {
        // Tested - Works
        if (res != null) {
            res.next();
            int offerId = res.getInt("offerId");
            double offeredRate = res.getDouble("offeredRate");
            String offerStatus = res.getString("offerStatus");
            String clientUsername = res.getString("clientId");
            String providerUsername = res.getString("providerId");
            return new Offer(offerId, offeredRate, offerStatus, clientUsername, providerUsername);
        }
        return null;
    }

    public static void main(String[] args) {
        Offer testOffer1 = Offer.getOfferWithId(1);
        System.out.println(testOffer1.offeredRate);

        Offer testOffer2 = Offer.getOfferWithClientAndProvider("ClientAdmin", "ProviderAdmin");
        System.out.println(testOffer2.offerStatus);
    }
}
