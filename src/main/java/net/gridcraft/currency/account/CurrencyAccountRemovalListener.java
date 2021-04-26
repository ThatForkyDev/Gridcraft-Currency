package net.gridcraft.currency.account;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import net.gridcraft.core.database.BaseDatabase;

import java.util.Objects;
import java.util.UUID;

public class CurrencyAccountRemovalListener implements RemovalListener<UUID, CurrencyAccount> {
    @Override
    public void onRemoval(RemovalNotification notification) {
        CurrencyAccount account = (CurrencyAccount) notification.getValue();

        if (Objects.isNull(account))
            return;

        CurrencyDAO.setBalance(
                BaseDatabase.getInstance().getConnection(),
                account.getUid(),
                account.getBalance()
        );
    }
}
