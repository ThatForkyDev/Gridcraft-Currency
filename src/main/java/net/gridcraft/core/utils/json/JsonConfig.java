package net.gridcraft.core.utils.json;

import com.google.common.base.Supplier;
import net.gridcraft.core.utils.json.annotation.ConfigName;
import net.gridcraft.core.utils.json.annotation.Jsonable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JsonConfig {
    public static <T> T load(File folder, Class<T> clazz, Supplier<T> supplier) {
        if (Objects.nonNull(folder) && clazz.isAnnotationPresent(Jsonable.class) && clazz.isAnnotationPresent(ConfigName.class)) {
            ConfigName configName = clazz.getAnnotation(ConfigName.class);

            if (!folder.exists())
                folder.mkdirs();

            String finalName = configName.value();

            File file = new File(folder, finalName);
            System.out.println(file.toString());
            if (!file.exists()) {
                saveFileWithObject(file, supplier.get());
            } else {
                try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
                    T result = GsonUtil.getGson().fromJson(reader, clazz);
                    return result != null ? result : supplier.get();
                } catch (IOException ignored) {}
            }
        }

        return supplier.get();
    }

    public static void saveFileWithObject(File file, Object object) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            GsonUtil.getGson().toJson(object, writer);
        } catch (IOException e) {
            throw new IllegalStateException("Saving " + file, e);
        }
    }
}
