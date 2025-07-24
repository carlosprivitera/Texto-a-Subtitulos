package main.java;

public class CompararCadenas {

	public CompararCadenas() {
		// TODO Auto-generated constructor stub
	}

    /**
     * Compara dos cadenas y devuelve un valor entero entre 0 y 100
     * que representa el porcentaje de similitud basado en la distancia de Levenshtein.
     * 
     * @param s1 Primera cadena
     * @param s2 Segunda cadena
     * @return Porcentaje de similitud (0 a 100)
     */
    public static int comparar(String s1, String s2) {
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException("Las cadenas no pueden ser null");
        }

        int len1 = s1.length();
        int len2 = s2.length();

        if (len1 == 0 && len2 == 0) return 100;
        if (len1 == 0 || len2 == 0) return 0;

        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) dp[i][0] = i;
        for (int j = 0; j <= len2; j++) dp[0][j] = j;

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int costo = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(
                    Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                    dp[i - 1][j - 1] + costo
                );
            }
        }

        int distancia = dp[len1][len2];
        int maxLen = Math.max(len1, len2);
        double similitud = 1.0 - ((double) distancia / maxLen);

        return (int) Math.round(similitud * 100);
    }
	
}
