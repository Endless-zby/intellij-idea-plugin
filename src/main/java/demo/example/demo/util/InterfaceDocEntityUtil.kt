package demo.example.demo.util

import com.alibaba.excel.annotation.ExcelProperty
import demo.example.demo.entity.InterfaceDocEntity
import java.util.*

object InterfaceDocEntityUtil {
    @JvmStatic
    fun getPropertyMap(entity: InterfaceDocEntity?): Map<String, Any> {
        val excelPropertyMap: MutableMap<String, Any> = HashMap()
        val fields = InterfaceDocEntity::class.java.declaredFields
        for (field in fields) {
            if (field.isAnnotationPresent(ExcelProperty::class.java)) {
                try {
                    field.isAccessible = true
                    val value = field[entity] ?: ""
                    val excelProperty = field.getAnnotation(ExcelProperty::class.java)
                    val key = excelProperty.value.contentToString()
                    excelPropertyMap[key.substring(1, key.length - 1)] = value
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
        }
        return excelPropertyMap
    }

    @JvmStatic
    val excelPropertyValues: Array<String>
        get() {
            val values: MutableList<String> = ArrayList()
            val fields = InterfaceDocEntity::class.java.declaredFields
            for (field in fields) {
                if (field.isAnnotationPresent(ExcelProperty::class.java)) {
                    val excelProperty = field.getAnnotation(ExcelProperty::class.java)
                    val value = excelProperty.value.contentToString()
                    values.add(value.substring(1, value.length - 1))
                }
            }
            return values.toTypedArray<String>()
        }
}
