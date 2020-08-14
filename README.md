# 注解
## 什么是注解
通俗的理解注解就是标签，给代码打标签，给代码打标签有啥用处？

* 格式检查：告诉编译器信息，比如被@Override标记的方法如果不是父类的某个方法，IDE会报错；

* 减少配置：运行时动态处理，得到注解信息，实现代替配置文件的功能；

* 减少重复工作：可以生成.java文件；
## 元注解
元注解也是一张标签，但是它是一张特殊的标签，它的作用和目的就是给其他普通的标签进行解释说明的。通俗就是有几种基础标签用。使用一个自定义标签都需要几种标签一起组合使用。

元标签有 @Retention、@Documented、@Target、@Inherited、@Repeatable 5 种。

### @Retention
Retention 的英文意为保留期的意思。当 @Retention 应用到一个注解上的时候，它解释说明了这个注解的的存活时间。

它的取值如下：

* RetentionPolicy.SOURCE 注解只在源码阶段保留，在编译器进行编译时它将被丢弃忽视。
* RetentionPolicy.CLASS 注解只被保留到编译进行的时候，它并不会被加载到 JVM 中。
* RetentionPolicy.RUNTIME 注解可以保留到程序运行的时候，它会被加载进入到 JVM 中，所以在程序运行时可以获取到它们。

### @Target
Target 是目标的意思，@Target 指定了注解运用的地方。

你可以这样理解，当一个注解被 @Target 注解时，这个注解就被限定了运用的场景。

类比到标签，原本标签是你想张贴到哪个地方就到哪个地方，但是因为 @Target 的存在，它张贴的地方就非常具体了，比如只能张贴到方法上、类上、方法参数上等等。@Target 有下面的取值

* ElementType.ANNOTATION_TYPE 可以给一个注解进行注解
* ElementType.CONSTRUCTOR 可以给构造方法进行注解
* ElementType.FIELD 可以给属性进行注解
* ElementType.LOCAL_VARIABLE 可以给局部变量进行注解
* ElementType.METHOD 可以给方法进行注解
* ElementType.PACKAGE 可以给一个包进行注解
* ElementType.PARAMETER 可以给一个方法内的参数进行注解
* ElementType.TYPE 可以给一个类型进行注解，比如类、接口、枚举

## 使用
### 编译时注解,就是生产.java
简单注解实例，如下就给CharlesAnnotation类用元注解声明了一个注解，就是说明他是一个标签

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.CLASS)
	public @interface CharlesAnnotation {
	    String name() default "charles";
	    String text() default  "";
	}

标签使用的地方，例如这样就给把标签贴在MainActivity上

	@CharlesAnnotation(
	        name = "other",
	        text = "new"
	)
	public class MainActivity extends AppCompatActivity {

需要用到auto-service,也可用JavaPoet

	private void analysisAnnotated(Element classElement) {
        CharlesAnnotation annotation = classElement.getAnnotation(CharlesAnnotation.class);
        String name = annotation.name();
        String text = annotation.text();

### 其他两种使用
可以参考[https://www.jianshu.com/p/5cac4cb9be54](https://www.jianshu.com/p/5cac4cb9be54)
