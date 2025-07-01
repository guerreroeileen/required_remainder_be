package com.example.required_remainder_be.service;

import com.example.required_remainder_be.dto.RequiredRemainderRequest;
import com.example.required_remainder_be.exception.RequiredRemainderException;
import com.example.required_remainder_be.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.required_remainder_be.constants.GeneralConstants.N_MUST_BE_BETWEEN_Y_AND_10_9;
import static com.example.required_remainder_be.constants.GeneralConstants.ONE_BILLION;
import static com.example.required_remainder_be.constants.GeneralConstants.TWO;
import static com.example.required_remainder_be.constants.GeneralConstants.X_MUST_BE_BETWEEN_2_AND_10_9;
import static com.example.required_remainder_be.constants.GeneralConstants.Y_MUST_BE_BETWEEN_0_AND_X_1;
import static com.example.required_remainder_be.constants.GeneralConstants.ZERO;

@Service
@Slf4j
public class RequiredRemainderService {

    /**
     * Solves test cases and returns List<Long> with results
     * 
     * @param requests List of test cases
     * @return List of results as Long values
     */
    public List<Long> solveCases(List<RequiredRemainderRequest> requests) {
        log.info("Solving {} test cases (List<Long> method)", requests.size());
        
        List<Long> results = new ArrayList<>();
        
        for (int i = 0; i < requests.size(); i++) {
            RequiredRemainderRequest request = requests.get(i);
            try {
                long result = findMaxK(request.getX(), request.getY(), request.getN());
                results.add(result);
                log.debug("Test case {}: success", i + 1);
            } catch (ValidationException e) {
                log.warn("Test case {}: validation error - {}", i + 1, e.getMessage());
                results.add(null);
            } catch (RequiredRemainderException e) {
                log.warn("Test case {}: calculation error - {}", i + 1, e.getMessage());
                results.add(null);
            }
        }
        
        log.info("Completed solving {} test cases (List<Long> method)", requests.size());
        return results;
    }

    /**
     * Finds the maximum integer k such that 0 ≤ k ≤ n and k mod x = y.
     * I decided to use long instead of big integer because big integer is slow in comparison
     * with long
     * @param x The divisor (2 ≤ x ≤ 10^9)
     * @param y The remainder (0 ≤ y < x)
     * @param n The upper bound (y ≤ n ≤ 10^9)
     * @return The maximum k that satisfies the conditions
     * @throws ValidationException if the constraints are not met
     */
    public long findMaxK(long x, long y, long n) {
        log.debug("Finding max k for x={}, y={}, n={}", x, y, n);
        
        // Validate constraints
        validateConstraints(x, y, n);
        
        // The key insight: k = q * x + y, where q is the quotient
        // We need to find the maximum q such that k ≤ n
        // So: (q * x) + y ≤ n
        // Therefore: q ≤ (n - y) / x
        
        long maxQuotient = (n - y) / x;
        long result = maxQuotient * x + y;

        if (result > n || result < ZERO) {
            throw new RequiredRemainderException(
                "No valid solution found for the given constraints",
                HttpStatus.UNPROCESSABLE_ENTITY,
                HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase()
            );
        }
        log.debug("Found result: k={} for x={}, y={}, n={}", result, x, y, n);
        return result;
    }

    /**
     * Validates the input constraints
     *
     * @param x The divisor
     * @param y The remainder
     * @param n The upper bound
     * @throws ValidationException if constraints are not met
     */
    private void validateConstraints(long x, long y, long n) {
        if (x < TWO || x > ONE_BILLION) {
            throw new ValidationException(
                X_MUST_BE_BETWEEN_2_AND_10_9,
                "x"
            );
        }
        if (y < ZERO || y >= x) {
            throw new ValidationException(
                Y_MUST_BE_BETWEEN_0_AND_X_1,
                "y"
            );
        }
        if (n < y || n > ONE_BILLION) {
            throw new ValidationException(
                N_MUST_BE_BETWEEN_Y_AND_10_9,
                "n"
            );
        }
    }
}