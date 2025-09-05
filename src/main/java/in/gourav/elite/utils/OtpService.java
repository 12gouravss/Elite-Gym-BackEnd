package in.gourav.elite.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private static final long OTP_VALID_DURATION_MINUTES = 5;

    private static class OtpInfo {
        String otp;
        LocalDateTime expiryTime;

        OtpInfo(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }
    
    //Multiple users may hit the OTP endpoints simultaneously
    //cuncurrentHashmap -> Allow multiple threads to read and write safely

    private final Map<String, OtpInfo> otpStorage = new ConcurrentHashMap<>();
    private final Map<String, String> resetTokenStorage = new ConcurrentHashMap<>();
    
  

    // Save OTP
    public void saveOtp(String email, String otp) {
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(OTP_VALID_DURATION_MINUTES);
        //plusMinutes(...) -> Adds X minutes to it.
        otpStorage.put(email, new OtpInfo(otp, expiryTime));
    }

    // Validate OTP
    public boolean validateOtp(String email, String otp) {
    
        OtpInfo otpInfo = otpStorage.get(email);

        if (otpInfo == null) return false;
        

        boolean isValid = otpInfo.otp.equals(otp) && LocalDateTime.now().isBefore(otpInfo.expiryTime);

        // If valid, remove it so it can't be reused
        if (isValid) otpStorage.remove(email);

        return isValid;
    }

    // clear OTP manually
    public void clearOtp(String email) {
        otpStorage.remove(email);
    }
    
    // Store Reset Token
    public void storeResetToken(String email, String token) {
        resetTokenStorage.put(email, token);
    }

    // Validate Reset Token
    public boolean validateResetToken(String email, String token) {
    	System.out.println(token);
    	System.out.println(resetTokenStorage.get(email));
        return token.equals(resetTokenStorage.get(email));
    }

    //  Clear Reset Token
    public void clearResetToken(String email) {
        resetTokenStorage.remove(email);
    }
}
