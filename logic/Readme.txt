GreenDAO3
环境：
1.项目的 build.gradle 中配置
    buildscript {
        repositories {
            jcenter()
        }
        dependencies {
            classpath 'org.greenrobot:greendao-gradle-plugin:3.1.0'
        }
    }

2.Module的 build.gradle 中配置
    // GreenDao
    apply plugin: 'org.greenrobot.greendao'
    greendao {
        // 指定数据库schema版本号，迁移等操作会用到
        schemaVersion 1
        // 通过gradle插件生成的数据库相关文件的包名，默认为你的entity所在的包名
        daoPackage 'com.erongdu.wireless.greendao.gen'
        // 生成的数据库文件默认目录为：build/generated/source/greendao
        // 自定义生成数据库文件的目录，可以将生成的文件放到java目录中，而不是build中，这样就不用额外的设置资源目录了
        targetGenDir 'src/main/java'
    }

    dependencies {
        // GreenDAO
        compile 'org.greenrobot:greendao:3.1.0'
    }



实体@Entity注解
- schema         告知GreenDao当前实体属于哪个schema
- active         无论是更新生成都刷新
- nameInDb       在数据库中的名字，如不写则为实体中类名
- indexes        定义索引，可以跨越多个列
- createInDb     是否创建表，默认为true,false时不创建

基础属性注解
- @Id           主键 long/Long型，可以通过@Id(autoincrement = true)设置自增长
- @Property     设置一个非默认关系映射所对应的列名，默认是的使用字段名 举例：@Property (nameInDb=”name”)
- @NotNul       设置数据库表当前列不能为空
- @Transient    表明这个字段不会被写入数据库，只是作为一个普通的java类字段，用来临时存储数据的，不会被持久化
- @OrderBy      排序

索引注解
- @Index        使用@Index作为一个属性来创建一个索引，通过name设置索引别名，也可以通过unique给索引添加约束
- @Unique       向数据库列添加了一个唯一的约束

关系注解
- @ToOne        定义与另一个实体（一个实体对象）的关系
- @ToMany       定义与多个实体对象的关系