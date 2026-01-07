package service;

import db.MongoConnection;
import org.bson.Document;
import com.mongodb.client.model.Updates;

import java.util.Scanner;

public class VotingService {

    static Scanner sc = new Scanner(System.in);

    public static void confirmNewElection() {
        MongoConnection.voters.updateMany(
                new Document(),
                Updates.set("hasVoted", false)
        );

        MongoConnection.candidates.updateMany(
                new Document(),
                Updates.set("votes", 0)
        );

        System.out.println("New election initialized.");
    }

    public static void showVoterCount() {
        long total = MongoConnection.voters.countDocuments();
        long voted = MongoConnection.voters.countDocuments(
                new Document("hasVoted", true)
        );

        System.out.println(
                "Voters: " + total +
                        " | Voted: " + voted +
                        " | Not Voted: " + (total - voted)
        );
    }

    public static void startVoting() {

        System.out.print("Enter Voter ID: ");
        String voterId = sc.nextLine();

        Document voter = MongoConnection.voters
                .find(new Document("voterId", voterId))
                .first();

        if (voter == null || voter.getBoolean("hasVoted")) {
            System.out.println("Invalid or already voted!");
            return;
        }

        CandidateService.viewCandidates();

        System.out.print("Enter Candidate ID: ");
        int cid = sc.nextInt();
        sc.nextLine();

        MongoConnection.candidates.updateOne(
                new Document("cid", cid),
                Updates.inc("votes", 1)
        );

        MongoConnection.voters.updateOne(
                new Document("voterId", voterId),
                Updates.set("hasVoted", true)
        );

        System.out.println("Vote cast successfully.");
    }

    public static void viewResults() {
        CandidateService.viewCandidates();
    }
}
