package pe.edu.vallegrande.sigrc.suppliers.domain.models;

import pe.edu.vallegrande.sigrc.suppliers.domain.exceptions.DomainException;

public enum DocumentType {

    DNI {
        @Override
        public String validationError(String number) {
            if (number == null || !number.matches("^[0-9]{8}$")) {
                return "DNI debe tener exactamente 8 dígitos numéricos";
            }
            return null;
        }
    },

    RUC {
        @Override
        public String validationError(String number) {
            if (number == null || !number.matches("^[0-9]{11}$")) {
                return "RUC debe tener exactamente 11 dígitos numéricos";
            }
            if (!number.startsWith("10") && !number.startsWith("20")) {
                return "RUC debe comenzar con 10 (persona natural con negocio) o 20 (persona jurídica)";
            }
            if (!hasValidCheckDigit(number)) {
                return "RUC inválido: el dígito verificador no corresponde al número ingresado";
            }
            return null;
        }
    };

    // Pesos oficiales SUNAT para el dígito verificador del RUC (módulo 11)
    private static final int[] RUC_WEIGHTS = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};

    /**
     * Valida el número según las reglas del tipo de documento.
     *
     * @return null si el número es válido; en caso contrario, el mensaje de error.
     */
    public abstract String validationError(String number);

    public boolean isValidNumber(String number) {
        return validationError(number) == null;
    }

    /**
     * Estructura del RUC peruano (SUNAT):
     *   RUC 10 = "10" + DNI del titular (8 dígitos) + dígito verificador  → persona natural con negocio
     *   RUC 20 = "20" + id asignado por SUNAT (8 dígitos) + dígito verificador → persona jurídica
     * El último dígito se verifica con el algoritmo módulo 11.
     */
    private static boolean hasValidCheckDigit(String ruc) {
        int sum = 0;
        for (int i = 0; i < RUC_WEIGHTS.length; i++) {
            sum += Character.getNumericValue(ruc.charAt(i)) * RUC_WEIGHTS[i];
        }
        int result = 11 - (sum % 11);
        int expected = (result == 10) ? 0 : (result == 11) ? 1 : result;
        return expected == Character.getNumericValue(ruc.charAt(10));
    }

    /**
     * Para RUC de persona natural (prefijo 10), los 8 dígitos centrales son el DNI del titular.
     *
     * @return el DNI embebido, o null si el RUC no es de persona natural.
     */
    public static String extractDniFromRuc(String ruc) {
        if (ruc != null && ruc.length() == 11 && ruc.startsWith("10")) {
            return ruc.substring(2, 10);
        }
        return null;
    }

    public static DocumentType from(String value) {
        try {
            return DocumentType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DomainException("Tipo de documento inválido: " + value + ". Use DNI o RUC");
        }
    }
}
