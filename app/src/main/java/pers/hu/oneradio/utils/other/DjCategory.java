package pers.hu.oneradio.utils.other;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.PARAMETER})
@IntDef(value = {Category.ANIME,Category.ARTICLE,Category.BOOK,Category.BUISNESS,Category.CITY,Category.COVER,Category.ELECTRIC,Category.HISTORY,Category.HOME,Category.KNOWLEDGE,Category.STORY})
public @interface DjCategory {
}
