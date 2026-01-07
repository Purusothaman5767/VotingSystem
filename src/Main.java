import service.*;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        VotingService.confirmNewElection();

        while (true) {
            VotingService.showVoterCount();

            System.out.println("\n--- ONLINE VOTING SYSTEM ---");
            System.out.println("1. Party Management");
            System.out.println("2. Register Candidate");
            System.out.println("3. View Candidates");
            System.out.println("4. Delete Candidate");
            System.out.println("5. Start Voting");
            System.out.println("6. View Results");
            System.out.println("7. Exit");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> PartyService.partyMenu();
                case 2 -> CandidateService.registerCandidate();
                case 3 -> CandidateService.viewCandidates();
                case 4 -> CandidateService.deleteCandidate();
                case 5 -> VotingService.startVoting();
                case 6 -> VotingService.viewResults();
                case 7 -> {
                    System.out.println("Application closed.");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
