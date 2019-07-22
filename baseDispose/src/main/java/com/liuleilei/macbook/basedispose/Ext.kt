package com.liuleilei.macbook.basedispose;

import android.util.Log

/**
 * create by liu
 * on2019/6/25
 */
/**
 * save cookies string
 */
fun encodeCookie(cookies: List<String>): String {
    val sb = StringBuffer()
    val set = HashSet<String>()
    cookies
            .map { cookie ->
                cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }
            .forEach {
                it.filterNot { set.contains(it) }.forEach { set.add(it) }
            }
    val ite = set.iterator()
    while (ite.hasNext()) {
        val cookie = ite.next()
        sb.append(cookie).append(";")
    }
    val last = sb.lastIndexOf(";")
    if (sb.length - 1 == last) {
        sb.deleteCharAt(last)
    }
    return sb.toString()
}
/**
 * Log
 */
fun loge(tag: String, content: String?) {
    Log.e(tag, content ?: tag)
}
