package springbook.user.sqlservice;

import org.springframework.context.annotation.Import;
import springbook.SqlServiceContext;

@Import(value = SqlServiceContext.class)
public @interface EnableSqlService {
}
