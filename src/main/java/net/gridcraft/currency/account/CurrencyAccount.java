package net.gridcraft.currency.account;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class CurrencyAccount {
    private static transient final LoadingCache<UUID, CurrencyAccount> ACCOUNTS_CACHE = CacheBuilder.newBuilder()
            .expireAfterAccess(24, TimeUnit.HOURS)
            .removalListener(new CurrencyAccountRemovalListener())
            .build(new CurrencyAccountCache());

    @Getter private UUID uid;
    @Getter @Setter private double balance;

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public void takeBalance(double amount) {
        this.balance -= amount;
    }

    public static CurrencyAccount of(UUID uuid) {
        try {
            return ACCOUNTS_CACHE.get(uuid);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Optional<CurrencyAccount> getIfPresent(UUID uuid) {
        return ACCOUNTS_CACHE
                .asMap()
                .values()
                .stream()
                .filter(account -> account.getUid().equals(uuid))
                .findFirst();
    }

    public void invalidate() {
        ACCOUNTS_CACHE.invalidate(uid);
    }
}
