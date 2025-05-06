package com.gdgknu.Inveed.security.dto;

public record KInvestLoginRes(
        String kInvestToken
) {
    public static KInvestLoginRes from(String kInvestToken) {
        return new KInvestLoginRes(kInvestToken);
    }
}
