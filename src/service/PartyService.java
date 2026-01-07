package service;

import db.MongoConnection;
import org.bson.Document;

import java.util.Random;
import java.util.Scanner;

public class PartyService {

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void partyMenu() {
        while (true) {
            System.out.println("\n1. Add Party\n2. Delete Party\n3. View Parties\n4. Back");
            int ch = sc.nextInt();
            sc.nextLine();

            if (ch == 1) addParty();
            else if (ch == 2) deleteParty();
            else if (ch == 3) viewParties();
            else break;
        }
    }

    static void addParty() {
        System.out.print("Party Name: ");
        String name = sc.nextLine();

        if (MongoConnection.parties.find(new Document("name", name)).first() != null) {
            System.out.println("Party exists!");
            return;
        }

        MongoConnection.parties.insertOne(
                new Document("pid", random.nextInt(9000) + 1000)
                        .append("name", name));
        System.out.println("Party added.");
    }

    static void deleteParty() {
        System.out.print("Party ID: ");
        int pid = sc.nextInt();
        sc.nextLine();

        MongoConnection.candidates.deleteMany(new Document("pid", pid));
        MongoConnection.parties.deleteOne(new Document("pid", pid));

        System.out.println("Party & candidates deleted.");
    }

    static void viewParties() {
        for (Document p : MongoConnection.parties.find()) {
            System.out.println(p.getInteger("pid") + " | " + p.getString("name"));
        }
    }
}
