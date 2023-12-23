package com.ownlab.ownlab_client.models

enum class RadarType(val key: String, val value: String) {
    passion("passion", "열정 P"),
    cooperation("cooperation", "팀워크 C"),
    responsibility("responsibility", "책임감 R"),
    diligence("diligence", "근면함 D"),
    conductivity("conductivity", "적응력 C"),
    leadership("leadership", "리더쉽 L")
}

inline fun <reified T : Enum<T>> findEnumByKey(key: String): T? {
    return enumValues<T>().firstOrNull { it is Enum<*> && (it as RadarType).key == key }
}