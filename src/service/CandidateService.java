package service;

import db.MongoConnection;
import org.bson.Document;

import java.util.Random;
import java.util.Scanner;

public class CandidateService {

    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void registerCandidate() {
        System.out.print("Voter ID: ");
        String voterId = sc.nextLine();

        Document voter = MongoConnection.voters
                .find(new Document("voterId", voterId)).first();

        if (voter == null) {
            System.out.println("Voter not found!");
            return;
        }

        PartyService.viewParties();
        System.out.print("Party ID: ");
        int pid = sc.nextInt();
        sc.nextLine();

        Document party = MongoConnection.parties
                .find(new Document("pid", pid)).first();

        if (party == null) {
            System.out.println("Invalid party!");
            return;
        }

        MongoConnection.candidates.insertOne(
                new Document("cid", random.nextInt(9000) + 1000)
                        .append("voterId", voterId)
                        .append("name", voter.getString("name"))
                        .append("pid", pid)
                        .append("party", party.getString("name"))
                        .append("votes", 0));

        System.out.println("Candidate registered.");
    }

    public static void viewCandidates() {
        for (Document c : MongoConnection.candidates.find()) {
            System.out.println(
                    c.getInteger("cid") + " | " +
                            c.getString("name") + " | " +
                            c.getString("party") + " | " +
                            c.getInteger("votes"));
        }
    }

    public static void deleteCandidate() {
        System.out.print("Candidate ID: ");
        int cid = sc.nextInt();
        sc.nextLine();

        MongoConnection.candidates.deleteOne(new Document("cid", cid));
        System.out.println("Candidate deleted.");
    }
}
