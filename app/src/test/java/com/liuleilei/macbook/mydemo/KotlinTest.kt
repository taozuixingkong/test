package com.liuleilei.macbook.mydemo

import org.junit.Test

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
}