function executeExercise1(value) {
    if (value > 10) {
        writeInDocument("Resposta: valor maior que 10", "exercise_1_result");
    } else if (value < 10) {
        writeInDocument("Resposta: valor menor que 10", "exercise_1_result");
    } else if (value == 10) {
        writeInDocument("Resposta: valor igual a 10", "exercise_1_result");
    } else {
        writeInDocument("Resposta: valor inválido", "exercise_1_result");
    }
}

function executeExercise2(value1, value2) {
    var floatValue1 = convertToFloatIfPossible(value1)
    var floatValue2 = convertToFloatIfPossible(value2)
    if (floatValue1 == null) {
        return writeInDocument("Resposta: valor 1 inválido", "exercise_2_result");
    }
    if (floatValue2 == null) {
        return writeInDocument("Resposta: valor 2 inválido", "exercise_2_result");
    }
    var result = floatValue1 + floatValue2
    writeInDocument("Resposta: " + result, "exercise_2_result");
}

function executeExercise3(value1, value2, operator) {
    var floatValue1 = convertToFloatIfPossible(value1)
    var floatValue2 = convertToFloatIfPossible(value2)
    if (floatValue1 == null) {
        return writeInDocument("Resposta: valor 1 inválido", "exercise_3_result");
    }
    if (floatValue2 == null) {
        return writeInDocument("Resposta: valor 2 inválido", "exercise_3_result");
    }
    var result = performOperation(floatValue1, floatValue2, operator)
    writeInDocument("Resposta: " + result, "exercise_3_result")
}

function executeExercise4(name, value) {
    value = convertToIntIfPossible(value)
    if (value == null) {
        return writeInDocument("Resposta: valor inválido", "exercise_4_result");
    }
    var result = "Resposta: "
    for (var i = 0; i < value ; i++) {
        result = result + " " + name
    }
    writeInDocument(result, "exercise_4_result")
}

function executeExercise5(side1, side2, side3) {
    var floatValue1 = convertToFloatIfPossible(side1)
    var floatValue2 = convertToFloatIfPossible(side2)
    var floatValue3 = convertToFloatIfPossible(side3)
    if (floatValue1 == null) {
        return writeInDocument("Resposta: lado 1 inválido", "exercise_5_result");
    }
    if (floatValue2 == null) {
        return writeInDocument("Resposta: lado 2 inválido", "exercise_5_result");
    }
    if (floatValue3 == null) {
        return writeInDocument("Resposta: lado 3 inválido", "exercise_5_result");
    }
    if (floatValue1 == floatValue2 && floatValue2 == floatValue3) {
        writeInDocument("Resposta: triângulo equilátero", "exercise_5_result");
    } else if (floatValue1 != floatValue2 && floatValue2 != floatValue3) {
        writeInDocument("Resposta: triângulo escaleno", "exercise_5_result");
    } else {
        writeInDocument("Resposta: triângulo isósceles", "exercise_5_result");
    }
}

function executeExercise6(kwh, price) {
    kwh = convertToFloatIfPossible(kwh)
    price = convertToFloatIfPossible(price)
    if (kwh == null) {
        return writeInDocument("Resposta: Quantidade inválida de KWh", "exercise_3_result");
    }
    if (price == null) {
        return writeInDocument("Resposta: Preço inválido", "exercise_3_result");
    }
    var result = kwh * price
    if (kwh > 200) {
        result *= 1.50
    } else if (kwh > 100) {
        result *= 1.25
    }
    writeInDocument(result, "exercise_6_result")
}

// Private
function performOperation(value1, value2, operator) {
    switch (operator) {
        case "sum": return value1 + value2
        case "subtraction": return value1 - value2
        case "multiplication": return value1 * value2
        case "division": return value1 / value2
    }
}

// Convenience
function writeInDocument(value, document_id) {
    var element = document.getElementById(document_id);
	element.innerHTML = value;
}

function convertToFloatIfPossible(value) {
    if (isNaN(value)) {
        return null
    }
    return parseFloat(value)
}

function convertToIntIfPossible(value) {
    if (isNaN(value)) {
        return null
    }
    return parseInt(value)
}
