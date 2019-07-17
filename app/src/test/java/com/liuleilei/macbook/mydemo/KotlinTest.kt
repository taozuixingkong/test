package com.liuleilei.macbook.mydemo

import org.junit.Test
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
create by liu
on2019/7/9
 */
class KotlinTest {
    /*接口*/
    interface MyInterface {
        var name: String
        fun bar()
        fun foo() {
            println("foo")
        }
    }

    class Child : MyInterface {
        override var name: String = "runoob"
        override fun bar() {
            println("bar")
        }
    }

    @Test
    fun main() {
        val c = Child()
        c.foo()
        c.bar()
        println(c.name)

    }

    /*
     * 函数重写
     */
    interface A {
        fun foo() {
            print("A")
        }

        fun bar()
    }

    interface B {
        fun foo() {
            print("B")
        }

        fun bar() {
            print("bar")
        }
    }

    class C : A {
        override fun bar() {
            print("bar")
        }

    }

    class D : A, B {
        override fun foo() {
            super<A>.foo()
            super<B>.foo()
        }

        override fun bar() {
            super<B>.bar()
        }

    }

    @Test
    fun testFunction() {
        val d = D()
        d.foo()
        d.bar()
    }

    /*
    扩展类
     */
    class User(var name: String)

    /*扩展函数*/
    fun User.Print() {
        print("用户名 $name")
    }

    @Test
    fun testAdd() {
        var user = User("Runoob")
        user.Print()
    }

    /*下面代码为 MutableList 添加一个swap 函数：*/

    // 扩展函数 swap,调换不同位置的值
    fun MutableList<Int>.swap(index1: Int, index2: Int) {
        val tmp = this[index1]     //  this 对应该列表
        this[index1] = this[index2]
        this[index2] = tmp
    }

    @Test
    fun main1() {

        val l = mutableListOf(1, 2, 3)
        // 位置 0 和 2 的值做了互换
        l.swap(0, 1) // 'swap()' 函数内的 'this' 将指向 'l' 的值

        println(l.toString())
    }

    /*扩展函数是静态解析的*/
    /**
     * 扩展函数是静态解析的，并不是接收者类型的虚拟成员，在调用扩展函数时，具体被调用的的是哪一个函数，
     * 由调用函数的的对象表达式来决定的，而不是动态的类型决定的:
     * */
    open class C1

    class D1 : C1()

    fun C1.foo() = "c"   // 扩展函数 foo

    fun D1.foo() = "d"   // 扩展函数 foo

    fun printFoo(c: C1) {
        println(c.foo())  // 类型是 C 类
    }

    @Test
    fun main2() {
        printFoo(D1())
    }

    /*若扩展函数和成员函数一致，则使用该函数时，会优先使用成员函数。*/
    class C2 {
        fun foo() {
            println("成员函数")
        }
    }

    fun C2.foo() {
        println("扩展函数")
    }

    @Test
    fun main3() {
        var c = C2()
        c.foo()
    }

    /*在扩展函数内， 可以通过 this 来判断接收者是否为 NULL,这样，即使接收者为 NULL,也可以调用扩展函数。*/
    fun Any?.toString(): String {
        if (this == null) return "null"
        // 空检测之后，“this”会自动转换为非空类型，所以下面的 toString()
        // 解析为 Any 类的成员函数
        return toString()
    }

    @Test
    fun main4() {
        var t = null
        println(t.toString())
    }

    /*伴生对象的扩展
如果一个类定义有一个伴生对象 ，你也可以为伴生对象定义扩展函数和属性。

伴生对象通过"类名."形式调用伴生对象，伴生对象声明的扩展函数，通过用类名限定符来调用：*/
    class MyClass {
        companion object {}  // 将被称为 "Companion"
    }

    fun MyClass.Companion.foo() {
        println("伴随对象的扩展函数")
    }

    val MyClass.Companion.no: Int
        get() = 10

    @Test
    fun main5() {
        println("no:${MyClass.no}")
        MyClass.foo()
    }

    /*扩展声明为成员
在一个类内部你可以为另一个类声明扩展。

在这个扩展中，有个多个隐含的接受者，其中扩展方法定义所在类的实例称为分发接受者，而扩展方法的目标类型的实例称为扩展接受者。*/
    class D3 {
        fun bar() {
            println("D bar")
        }
    }

    class C3 {
        fun baz() {
            println("C baz")
        }

        fun D3.foo() {
            bar()   // 调用 D.bar
            baz()   // 调用 C.baz
        }

        fun caller(d: D3) {
            d.foo()   // 调用扩展函数
        }
    }

    @Test
    fun main6() {
        val c: C3 = C3()
        val d: D3 = D3()
        c.caller(d)

    }

    /*假如在调用某一个函数，而该函数在分发接受者和扩展接受者均存在，则以扩展接收者优先，要引用分发接收者的成员你可以使用限定的 this 语法。*/
    class D4 {
        fun bar() {
            println("D bar")
        }
    }

    class C4 {
        fun bar() {
            println("C bar")
        }  // 与 D 类 的 bar 同名

        fun D4.foo() {
            bar()         // 调用 D.bar()，扩展接收者优先
            this@C4.bar()  // 调用 C.bar()
        }

        fun caller(d: D4) {
            d.foo()   // 调用扩展函数
        }
    }

    @Test
    fun main7() {
        val c: C4 = C4()
        val d: D4 = D4()
        c.caller(d)

    }

    /*使用 copy 类复制 User 数据类，并修改 age 属性:*/
    data class User1(val name: String, val age: Int)

    @Test
    fun main8() {
        val jack = User1(name = "Jack", age = 1)
        val olderJack = jack.copy(age = 2)
        println(jack)
        println(olderJack)
        val jane = User1("Jane", 35)
        val (name, age) = jane
        println("$name, $age years of age") // prints "Jane, 35 years of age"
    }

    /*以下实例向泛型类 Box 传入整型数据和字符串：*/
    class Box<T>(t: T) {
        var value = t
    }

    @Test
    fun main9() {
        var boxInt = Box<Int>(10)
        var boxString = Box<String>("Runoob")

        println(boxInt.value)
        println(boxString.value)
    }

    /*在调用泛型函数时，如果可以推断出类型参数，可以省略泛型参数。

以下实例创建了泛型函数 doPrintln，函数根据传入的不同类型做相应处理：*/
    @Test
    fun main10() {
        val age = 23
        val name = "runoob"
        val bool = true

        doPrintln(age)    // 整型
        doPrintln(name)   // 字符串
        doPrintln(bool)   // 布尔型
    }

    fun <T> doPrintln(content: T) {

        when (content) {
            is Int -> println("整型数字为 $content")
            is String -> println("字符串转换为大写：${content.toUpperCase()}")
            else -> println("T 不是整型，也不是字符串")
        }
    }

    /*型变
    Kotlin 中没有通配符类型，它有两个其他的东西：声明处型变（declaration-site variance）与类型投影（type projections）。

    声明处型变
    声明处的类型变异使用协变注解修饰符：in、out，消费者 in, 生产者 out。

    使用 out 使得一个类型参数协变，协变类型参数只能用作输出，可以作为返回值类型但是无法作为入参的类型：*/
// 定义一个支持协变的类
    class Runoob<out A>(val a: A) {
        fun foo(): A {
            return a
        }
    }

    @Test
    fun main11() {
        var strCo: Runoob<String> = Runoob("a")
        var anyCo: Runoob<Any> = Runoob<Any>("b")
        anyCo = strCo
        println(anyCo.foo())   // 输出 a
    }

    /*in 使得一个类型参数逆变，逆变类型参数只能用作输入，可以作为入参的类型但是无法作为返回值的类型：*/
// 定义一个支持逆变的类
    class Runoob1<in A>(a: A) {
        fun foo(a: A) {
            print(a)
        }
    }

    @Test
    fun main12() {
        var strDCo = Runoob1("a")
        var anyDCo = Runoob1<Any>("b")
        strDCo = anyDCo
        strDCo.foo("b")
    }

    /*请注意，匿名对象可以用作只在本地和私有作用域中声明的类型。如果你使用匿名对象作为公有函数的 返回类型或者用作公有属性的类型，
    那么该函数或属性的实际类型 会是匿名对象声明的超类型，如果你没有声明任何超类型，就会是 Any。在匿名对象 中添加的成员将无法访问*/
    class C5 {
        // 私有函数，所以其返回类型是匿名对象类型
        private fun foo() = object {
            val x: String = "x"
        }

        // 公有函数，所以其返回类型是 Any
        fun publicFoo() = object {
            val x: String = "x"
        }

        fun bar() {
            val x1 = foo().x        // 没问题
            // val x2 = publicFoo().x  // 错误：未能解析的引用“x”
        }
    }

    /*当然你也可以定义一个变量来获取获取这个对象，当时当你定义两个不同的变量来获取这个对象时，
    你会发现你并不能得到两个不同的变量。也就是说通过这种方式，我们获得一个单例。*/
    /*以下实例中，两个对象都输出了同一个 url 地址：*/
    object Site {
        var url: String = ""
        val name: String = "菜鸟教程"
    }

    @Test
    fun main13() {
        var s1 = Site
        var s2 = Site
        s1.url = "www.runoob.com"
        println(s1.url)
        println(s2.url)
    }

    /*与对象表达式不同，当对象声明在另一个类的内部时，这个对象并不能通过外部类的实例访问到该对象，
    而只能通过类名来访问，同样该对象也不能直接访问到外部类的方法和变量。*/
    class Site1 {
        var name = "菜鸟教程"

        object DeskTop {
            var url = "www.runoob.com"
            fun showName() {
                //  print{"desk legs $name"} // 错误，不能访问到外部类的方法和变量
            }
        }
    }

    fun main(args: Array<String>) {
        var site = Site1()
        // site.DeskTop.url // 错误，不能通过外部类的实例访问到该对象
        Site1.DeskTop.url // 正确
    }

    /*9*/
    var totalMoney: Float = 3f
    var payMoney: Float = 0f
    var purMoney: Float = 65f
    @Test
    fun main14() {
        for (ab in 1..12) {
            if (ab < 4) {
                payMoney = payMoney + 65 * (3 - (ab - 1) * 0.5f)
            } else if (ab == 4) {
                payMoney = payMoney + 1.25f * 0.5f * 65
            } else if (ab > 9) {
                payMoney = payMoney + 65 * (1.25f - (ab - 9) * 0.5f)
            }
            println(payMoney)
        }
        payMoney = payMoney + 65 * 1.25f * 5
        println(payMoney)
    }

    /*类委托
    类的委托即一个类中定义的方法实际是调用另一个类的对象的方法来实现的。

    以下实例中派生类 Derived 继承了接口 Base 所有方法，并且委托一个传入的 Base 类的对象来执行这些方法。*/
// 创建接口
    interface Base {
        fun print()
    }

    // 实现此接口的被委托的类
    class BaseImpl(val x: Int) : Base {
        override fun print() {
            print(x)
        }
    }

    // 通过关键字 by 建立委托类
    class Derived(b: Base) : Base by b

    @Test
    fun main15() {
        val b = BaseImpl(10)
        Derived(b).print() // 输出 10
        b.print()
    }

    /*在 Derived 声明中，by 子句表示，将 b 保存在 Derived 的对象实例内部，而且编译器将会生成继承自 Base 接口的所有方法, 并将调用转发给 b。*/
    /*属性委托
属性委托指的是一个类的某个属性值不是在类中直接进行定义，而是将其托付给一个代理类，从而实现对该类的属性统一管理。

属性委托语法格式：

val/var <属性名>: <类型> by <表达式>*/
/*定义一个被委托的类
该类需要包含 getValue() 方法和 setValue() 方法，且参数 thisRef 为进行委托的类的对象，prop 为进行委托的属性的对象。
by 关键字之后的表达式就是委托, 属性的 get() 方法(以及set() 方法)将被委托给这个对象的 getValue() 和 setValue() 方法。
属性委托不必实现任何接口, 但必须提供 getValue() 函数(对于 var属性,还需要 setValue() 函数)*/
    // 定义包含属性委托的类Process
    class Example {
        var p: String by Delegate1()
    }

    // 委托的类
    class Delegate1 {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "$thisRef, 这里委托了 ${property.name} 属性"
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            println("$thisRef 的 ${property.name} 属性赋值为 $value")
        }
    }

    @Test
    fun main16() {
        val e = Example()
        println(e.p)     // 访问该属性，调用 getValue() 函数

        e.p = "Runoob"   // 调用 setValue() 函数
        println(e.p)
    }

    /*标准委托
Kotlin 的标准库中已经内置了很多工厂方法来实现属性的委托。

延迟属性 Lazy
lazy() 是一个函数, 接受一个 Lambda 表达式作为参数, 返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托：
 第一次调用 get() 会执行已传递给 lazy() 的 lamda 表达式并记录结果， 后续调用 get() 只是返回记录的结果。

*/
    val lazyValue: String by lazy {
        println("computed!")     // 第一次调用输出，第二次调用不执行
        "Hello"
    }

    @Test
    fun main17() {
        println(lazyValue)   // 第一次执行，执行两次输出表达式
        println(lazyValue)   // 第二次执行，只输出返回值
    }

    /*可观察属性 Observable
observable 可以用于实现观察者模式。

Delegates.observable() 函数接受两个参数: 第一个是初始化值, 第二个是属性值变化事件的响应器(handler)。

在属性赋值后会执行事件的响应器(handler)，它有三个参数：被赋值的属性、旧值和新值：*/
    class User2 {
        var name: String by Delegates.observable("初始值") { prop, old, new ->
            println("旧值：$old -> 新值：$new")
        }
    }

    @Test
    fun main18() {
        val user = User2()
        user.name = "第一次赋值"
        user.name = "第二次赋值"
    }

    /*把属性储存在映射中
一个常见的用例是在一个映射（map）里存储属性的值。 这经常出现在像解析 JSON 或者做其他"动态"事情的应用中。
 在这种情况下，你可以使用映射实例自身作为委托来实现委托属性。*/
    class Site2(val map: Map<String, Any?>) {
        val name: String by map
        val url: String  by map
    }

    @Test
    fun main19() {
        // 构造函数接受一个映射参数
        val site = Site2(mapOf(
                "name" to "菜鸟教程",
                "url" to "www.runoob.com"
        ))

        // 读取映射值
        println(site.name)
        println(site.url)
    }

    /*如果使用 var 属性，需要把 Map 换成 MutableMap：*/
    class Site3(val map: MutableMap<String, Any?>) {
        val name: String by map
        val url: String by map
    }

    @Test
    fun main20() {

        var map: MutableMap<String, Any?> = mutableMapOf(
                "name" to "菜鸟教程",
                "url" to "www.runoob.com"
        )

        val site = Site3(map)

        println(site.name)
        println(site.url)

        println("--------------")
        map.put("name", "Google")
        map.put("url", "www.google.com")

        println(site.name)
        println(site.url)

    }

    /*Not Null
notNull 适用于那些无法在初始化阶段就确定属性值的场合。*/
    class Foo {
        var notNullBar: String by Delegates.notNull<String>()
    }

//    var foo: Foo()
//    foo.notNullBar = "bar"
//    println(foo.notNullBar)
/*lambda(匿名函数)
lambda表达式使用实例*/
    @Test
    fun main21(){
    val sumLambda:(Int,Int) -> Int = {x,y -> x+y}
    println(sumLambda(1,2))
}
}