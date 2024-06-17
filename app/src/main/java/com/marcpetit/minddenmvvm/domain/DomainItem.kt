package com.marcpetit.minddenmvvm.domain

open class DomainItem<T> (
    val data: T?,
    val error: String? = null
) {

    override fun toString(): String {
        return "DomainItem(data=$data, error=$error)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DomainItem<*>) return false

        if (data != other.data) return false
        if (error != other.error) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data?.hashCode() ?: 0
        result = 31 * result + (error?.hashCode() ?: 0)
        return result
    }

}
