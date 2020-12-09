# SecureDatabaseSystem

**简易安全数据库系统**

设计任务：该系统实现一个安全的数据库系统，对数据库进行数字签名保证完整性，数据加密保证隐秘性。

***设计要求：***

1. 数据库的数据要进行加密；

2. 对数据库的完整性进行保护；
3. 防止用户根据部分密文明文对，恢复数据库总密钥；
4. 数据采用一个密钥以某种形式衍生子密钥进行加密；
5. 保证密钥的安全性。

***说明：可以采用总密钥，根据hash函数，以及每一个数据的各种属性来产生子密钥。***

***详细解释：***

- **数据库的数据加密：可以给数据库加个背景，比如医院就诊人数据库，或者银行存款数据库等等，只对敏感信息加密即可。这样查询的时候仅对所需字段解密。**
- **数据库完整性保护：主要是对数据是否篡改进行检验，也就是说数据被篡改的话能够及时发现，例如可以利用消息验证码、hash验证或md5校验。**
- **防止用户利用部分明文密文对，来恢复数据库总密钥：可以通过增加复杂性来实现，比如可以利用AES算法。**
- **数据采用一个密钥通过某种方式来衍生出子密钥加密：可以根据一个总密钥通过hash函数生成子密钥， （其中方便解密可以一个字段采用一个子密钥）**
- **保证密钥安全性：可以通过第三方认证的方式，或设置专门的数据库管理。**

---------

数据库往往是企业最为核心的数据保护对象，与传统的网络安全防护体系不同，数据库安全技术更加注重从客户内部的角度做安全。其内涵包括了保密性、完整性和可用性，即所谓的CIA(Confidentiality, Integrity, Availability)三个方面。

（1）保密性：不允许未经授权的用户存取信息；

（2）完整性：只允许被授权的用户修改数据；

（3）可用性：不应拒绝已授权的用户对数据进行存取。

### 分析

![image-20201116100956298](https://cdn.jsdelivr.net/gh/kklll/Resources@master/pics/image-20201116100956298.png)

我们要做的就是写一个中间层，使用此中间层进行加密和解密，目的就是为了当数据库发生一些SQL注入或者某些攻击的时候泄露数据之后攻击者也没法得到有效的数据。

***加解密算法选择：***

- 对称加密---使用AES算法

选用***医院就诊数据库***进行实现上述系统。

功能：

- 医生登录后可以增删改查病人信息，同时对数据一致性进行校验，***篡改的话警告管理员***
- 医生每次修改和增加的时候对数据库进行签名，每次查询前先检查签名。

#### 数据库实现

***数据表***

***患者姓名、性别、住址、身份证号码、出生日期、电话、主治医生、症状、所属科室、病史、医嘱、就诊日期、出院日期、***

以此作为例子，其中***患者姓名、性别、住址、身份证号码、出生日期、电话、症状、病史、医嘱需要进行加密***。

当然不仅仅局限该表进行加密，我们可以使用其中的方法进行其他表的加密，上述数据表只是为了方便演示使用。

```sql
CREATE TABLE `hospital_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `patient_name` varchar(255) DEFAULT NULL COMMENT '患者姓名',
  `sex` varchar(255) DEFAULT NULL COMMENT '患者性别',
  `address` varchar(255) DEFAULT NULL COMMENT '患者住址',
  `idcrad` varchar(255) DEFAULT NULL COMMENT '患者身份证号码',
  `birthday` varchar(255) DEFAULT NULL COMMENT '患者出生日期',
  `phone` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `doctor` varchar(255) DEFAULT NULL COMMENT '主治医生姓名',
  `symptom` varchar(255) DEFAULT NULL COMMENT '症状',
  `department` varchar(255) DEFAULT NULL COMMENT '所属科室',
  `medical_history` varchar(255) DEFAULT NULL COMMENT '病史',
  `doctor_order` varchar(255) DEFAULT NULL COMMENT '医嘱',
  `clinic_date` varchar(255) DEFAULT NULL COMMENT '就诊日期',
  `discharge_date` varchar(255) DEFAULT NULL COMMENT '出院日期',
  `sign` varchar(255) DEFAULT NULL COMMENT '签名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

#### 加密方式

> 字段加密算法：AES

**密钥=MD5(数据总密钥+字段名称)** 

**密文=AES(密钥，要加密的文本)**

> 签名的生成算法：MD5

**签名文本=每个字段值密文进行字符串拼接后+数据总密钥**

**签名=MD5(签名文本)**

### 效果图

![image-20201202163224064](https://cdn.jsdelivr.net/gh/kklll/Resources@master/pics/image-20201202163224064.png)

![image-20201202163505155](https://cdn.jsdelivr.net/gh/kklll/Resources@master/pics/image-20201202163505155.png)

![image-20201202163521111](https://cdn.jsdelivr.net/gh/kklll/Resources@master/pics/image-20201202163521111.png)

![image-20201202163556785](https://cdn.jsdelivr.net/gh/kklll/Resources@master/pics/image-20201202163556785.png)

表记录如上面，就是加密后的字段值是这样的。

### 怎样使用？

1. 克隆本项目 git clone  https://github.com/kklll/SecureDatabaseSystem.git
2. cd secureDatabaseSystem
3. cd web-design
4. npm install 
5. npm run serve ，进行到这一步表示你的前端已经跑起来了
6. 后端的话就下载好依赖，然后点击IDEA的RUN，就跑起来了，前提是要配置好你的数据库信息！

### 如何实现？

其实最核心的方法就是进行加密解密和签名。使用注解@SecretTable 标注你想要加密的实体类，使用@Secret标注你要加密的字段，然后在数据库前的方法中使用Tool类中的方法进行加密解密验证（当然Tool类中的解密方法会自动判断签名字段是否被篡改），如果被篡改了的话会给你设置好的邮箱发送邮件。当然自动检测增、删、改、查方法还没有实现、同时加密后的数据不能进行范围查询，只是个建议的项目，希望多多批评！如果有好的解决方法希望大家可以提一提PR，帮助我这个小菜鸡更好的理解！