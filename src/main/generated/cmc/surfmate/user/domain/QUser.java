package cmc.surfmate.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1069155792L;

    public static final QUser user = new QUser("user");

    public final DatePath<java.time.LocalDate> birthday = createDate("birthday", java.time.LocalDate.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath fcmToken = createString("fcmToken");

    public final EnumPath<cmc.surfmate.common.enums.Gender> gender = createEnum("gender", cmc.surfmate.common.enums.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath isDelete = createString("isDelete");

    public final BooleanPath isOwner = createBoolean("isOwner");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath phNum = createString("phNum");

    public final StringPath profileImage = createString("profileImage");

    public final EnumPath<cmc.surfmate.common.enums.Provider> provider = createEnum("provider", cmc.surfmate.common.enums.Provider.class);

    public final EnumPath<cmc.surfmate.common.enums.RoleType> roleType = createEnum("roleType", cmc.surfmate.common.enums.RoleType.class);

    public final StringPath uid = createString("uid");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

