## lucene 搜索示例

### 创建数据库表
```sql
CREATE SCHEMA `po_info` DEFAULT CHARACTER SET utf8;
	
CREATE TABLE `po_info`.`data` (
`id` INT NOT NULL AUTO_INCREMENT,
`title` VARCHAR(45) NULL,
`content` TEXT NULL,
`sourceType` INT NULL,
`createdAt` DATETIME NULL,
PRIMARY KEY (`id`));
```
	
### 初始化数据
```
执行
com.search.lucene.LuceneTest#initData
```
	
### 运行程序
[开始搜索](http://localhost:8080/lucene)
	
