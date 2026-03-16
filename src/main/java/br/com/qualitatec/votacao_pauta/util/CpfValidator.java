package br.com.qualitatec.votacao_pauta.util;

public class CpfValidator {

    public static boolean isValid(String cpf) {
        if (cpf == null) return false;

        // Remove caracteres não numéricos
        String numbers = cpfSoNumeros(cpf);

        // Verifica se todos os dígitos são iguais
        if (numbers.chars().distinct().count() == 1) return false;

        // Calcula o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (numbers.charAt(i) - '0') * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) firstDigit = 0;

        // Calcula o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += (numbers.charAt(i) - '0') * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) secondDigit = 0;

        // Verifica os dígitos
        return numbers.charAt(9) - '0' == firstDigit &&
                numbers.charAt(10) - '0' == secondDigit;
    }

    public static final String formatCpf(String cpf) {
        if (cpf == null) return null;
            String numbers = cpf.replaceAll("\\D", "");
        if (numbers.length() != 11) return null;
        return String.format("%s.%s.%s-%s",
                numbers.substring(0, 3),
                numbers.substring(3, 6),
                numbers.substring(6, 9),
                numbers.substring(9));
    }

    public static final String cpfSoNumeros(String cpf) {
        if (cpf != null)
            return cpf.replaceAll("\\D", "");

        return null;
    }
}