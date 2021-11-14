package net.elytrapvp.elytraduels.utils;

public class EloUtils {

    /**
     * A quick function to calculate Elo ratings. Follows algorithm found at
     * https://en.wikipedia.org/wiki/Elo_rating_system#Mathematical_details.
     *
     * This version returns a primitive int array with the new ratings, but you
     * can modify it as needed. The important variables to watch out for are "an"
     * and "bn", which hold the new ratings for player A and player B respectively.
     *
     * @param a
     *   The rating for player A.
     * @param b
     *   The rating for player B.
     * @param winner
     *   A string indicating the winner. Can be either "A" or "B", case-insensitive.
     * @param k
     *   The k-factor to use for the calculation. Higher values will make ratings
     *   change more drastically.
     * @return
     *   An int[] primitive array, with the first int being A's new rating, and the
     *   second being B's new ranking.
     */
    public static int[] calculateNewRatings(int a, int b, String winner, int k) {

        double ea = 1 / (1 + Math.pow(10, (b - a) / 400));
        double eb = 1 - ea;

        int sa = (winner.equalsIgnoreCase("a")) ? 1 : 0;
        int sb = 1 - sa;

        int an = (int) Math.floor(a + k * (sa - ea));
        int bn = (int) Math.floor(b + k * (sb - eb));

        return new int[]{an, bn};

    }

    // Function to calculate Elo rating
    // K is a constant.
    // d determines whether Player A wins
    // or Player B.
    public static int[] eloRating(float Ra, float Rb, int K, boolean d) {

        // To calculate the Winning
        // Probability of Player B
        float Pb = Probability(Ra, Rb);

        // To calculate the Winning
        // Probability of Player A
        float Pa = Probability(Rb, Ra);

        // Case -1 When Player A wins
        // Updating the Elo Ratings
        if (d == true) {
            Ra = Ra + K * (1 - Pa);
            Rb = Rb + K * (0 - Pb);
        }

        // Case -2 When Player B wins
        // Updating the Elo Ratings
        else {
            Ra = Ra + K * (0 - Pa);
            Rb = Rb + K * (1 - Pb);
        }

        double one = (Math.round(Ra * 1000000.0) / 1000000.0) + 0.5;
        double two = (Math.round(Rb * 1000000.0) / 1000000.0) + 0.5;

        return new int[]{(int) one, (int) two};
    }

    static float Probability(float rating1, float rating2) {
        return 1.0f / (1 + (float) (Math.pow(10, (rating1 - rating2) / 400)));
    }

}