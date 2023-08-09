package demo.example.demo.util

import com.intellij.openapi.project.Project
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClass
import com.intellij.psi.search.GlobalSearchScope

object CommonUtil {
    @JvmStatic
    @Throws(IllegalAccessException::class)
    fun convert(obj: Any): Map<String, Any> {
        val map: MutableMap<String, Any> = HashMap()
        var clazz: Class<*>? = obj.javaClass
        while (clazz != null) {
            val fields = clazz.declaredFields
            for (field in fields) {
                field.isAccessible = true
                val fieldName = field.name
                val fieldValue = field[obj]
                map[fieldName] = fieldValue ?: ""
            }
            clazz = clazz.superclass
        }
        return map
    }

    @JvmStatic
    fun getClassByName(className: String, project: Project): PsiClass? {
        val psiFacade = JavaPsiFacade.getInstance(project)
        return psiFacade.findClass(className, GlobalSearchScope.allScope(project))
    }
}
