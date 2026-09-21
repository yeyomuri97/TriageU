package edu.unicauca.aplimovil.composelble4.ui.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import edu.unicauca.aplimovil.composelble4.viewmodel.AppUiState
import java.util.Calendar

@Composable
fun NuevaActividadScreen(
    uiState: AppUiState,
    onTitleChange: (String) -> Unit,
    onSubjectChange: (String) -> Unit,
    onTypeChange: (String) -> Unit,
    onDateChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onPreparationChange: (Int) -> Unit,
    onEstimatedTimeChange: (String) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    var subjectMenuExpanded by remember {
        mutableStateOf(false)
    }

    var timeMenuExpanded by remember {
        mutableStateOf(false)
    }

    val activityTypes = listOf(
        "Parcial",
        "Tarea",
        "Proyecto",
        "Otro"
    )

    val timeOptions = listOf(
        "30 minutos",
        "1 hora",
        "1.5 horas",
        "2 horas",
        "2.5 horas",
        "3 horas",
        "3.5 horas",
        "4 horas",
        "4.5 horas",
        "5 horas",
        "5.5 horas",
        "6 horas",
        "6.5 horas",
        "7 horas",
        "7.5 horas",
        "8 horas",
        "8.5 horas",
        "9 horas",
        "9.5 horas",
        "10 horas"
    )


    fun showDatePicker() {

        val calendar = Calendar.getInstance()

        // Intenta abrir el calendario en la fecha
        // que ya aparece seleccionada en el formulario.
        val dateParts = uiState.newActivityDate
            .replace(" ", "")
            .split("/")

        if (dateParts.size == 3) {

            val day =
                dateParts[0].toIntOrNull()

            val month =
                dateParts[1].toIntOrNull()

            val year =
                dateParts[2].toIntOrNull()

            if (
                day != null &&
                month != null &&
                year != null
            ) {

                calendar.set(
                    year,
                    month - 1,
                    day
                )
            }
        }


        val datePickerDialog = DatePickerDialog(

            context,

            { _, year, month, day ->

                val selectedDate =
                    "%02d / %02d / %04d".format(
                        day,
                        month + 1,
                        year
                    )

                onDateChange(
                    selectedDate
                )
            },

            calendar.get(
                Calendar.YEAR
            ),

            calendar.get(
                Calendar.MONTH
            ),

            calendar.get(
                Calendar.DAY_OF_MONTH
            )
        )

        datePickerDialog.show()
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 20.dp,
                vertical = 20.dp
            )
    ) {


        // ------------------------------------------------
        // ENCABEZADO
        // ------------------------------------------------

        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBack
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ArrowBack,

                    contentDescription =
                        "Volver",

                    tint =
                        MaterialTheme
                            .colorScheme
                            .onBackground
                )
            }


            Text(
                text =
                    "Nueva actividad",

                style =
                    MaterialTheme
                        .typography
                        .headlineMedium,

                color =
                    MaterialTheme
                        .colorScheme
                        .onBackground
            )
        }


        Spacer(
            modifier =
                Modifier.height(8.dp)
        )


        // ------------------------------------------------
        // INFORMACIÓN BÁSICA
        // ------------------------------------------------

        Text(
            text =
                "Información básica",

            style =
                MaterialTheme
                    .typography
                    .titleMedium,

            color =
                MaterialTheme
                    .colorScheme
                    .onBackground
        )


        Spacer(
            modifier =
                Modifier.height(10.dp)
        )


        // ------------------------------------------------
        // MATERIA
        // ------------------------------------------------

        Text(
            text =
                "Materia",

            style =
                MaterialTheme
                    .typography
                    .bodySmall,

            color =
                MaterialTheme
                    .colorScheme
                    .onSurface
        )


        Spacer(
            modifier =
                Modifier.height(6.dp)
        )


        Box(
            modifier =
                Modifier.fillMaxWidth()
        ) {

            OutlinedButton(

                onClick = {
                    subjectMenuExpanded = true
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),

                shape =
                    RoundedCornerShape(12.dp),

                border =
                    BorderStroke(
                        width = 1.dp,
                        color =
                            MaterialTheme
                                .colorScheme
                                .outline
                    ),

                colors =
                    ButtonDefaults
                        .outlinedButtonColors(

                            containerColor =
                                MaterialTheme
                                    .colorScheme
                                    .surface,

                            contentColor =
                                MaterialTheme
                                    .colorScheme
                                    .onSurface
                        )
            ) {

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        text =
                            uiState
                                .newActivitySubject,

                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium
                    )


                    Icon(
                        imageVector =
                            Icons.Default
                                .ArrowDropDown,

                        contentDescription =
                            "Seleccionar materia"
                    )
                }
            }


            DropdownMenu(

                expanded =
                    subjectMenuExpanded,

                onDismissRequest = {
                    subjectMenuExpanded = false
                },

                containerColor =
                    MaterialTheme
                        .colorScheme
                        .surface,

                tonalElevation =
                    0.dp
            ) {

                uiState
                    .subjects
                    .forEach { subject ->

                        DropdownMenuItem(

                            text = {

                                Text(
                                    text =
                                        subject.name,

                                    style =
                                        MaterialTheme
                                            .typography
                                            .bodyMedium
                                )
                            },

                            onClick = {

                                onSubjectChange(
                                    subject.name
                                )

                                subjectMenuExpanded =
                                    false
                            }
                        )
                    }
            }
        }


        Spacer(
            modifier =
                Modifier.height(12.dp)
        )


        // ------------------------------------------------
        // ACTIVIDAD
        // ------------------------------------------------

        Text(
            text =
                "Actividad",

            style =
                MaterialTheme
                    .typography
                    .bodySmall,

            color =
                MaterialTheme
                    .colorScheme
                    .onSurface
        )


        Spacer(
            modifier =
                Modifier.height(6.dp)
        )


        OutlinedTextField(

            value =
                uiState.newActivityTitle,

            onValueChange =
                onTitleChange,

            modifier =
                Modifier.fillMaxWidth(),

            placeholder = {

                Text(
                    text = "Parcial 1"
                )
            },

            singleLine =
                true,

            shape =
                RoundedCornerShape(12.dp)
        )


        Spacer(
            modifier =
                Modifier.height(14.dp)
        )


        // ------------------------------------------------
        // TIPO
        // ------------------------------------------------

        Text(
            text =
                "Tipo",

            style =
                MaterialTheme
                    .typography
                    .bodySmall,

            color =
                MaterialTheme
                    .colorScheme
                    .onSurface
        )


        Spacer(
            modifier =
                Modifier.height(8.dp)
        )


        Row(
            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            activityTypes.forEach { type ->

                val selected =
                    uiState.newActivityType == type


                OutlinedButton(

                    onClick = {
                        onTypeChange(type)
                    },

                    modifier =
                        Modifier
                            .width(72.dp)
                            .height(40.dp),

                    shape =
                        RoundedCornerShape(20.dp),

                    contentPadding =
                        PaddingValues(
                            horizontal = 2.dp
                        ),

                    border =
                        BorderStroke(

                            width = 1.dp,

                            color =
                                if (selected) {

                                    MaterialTheme
                                        .colorScheme
                                        .primary

                                } else {

                                    MaterialTheme
                                        .colorScheme
                                        .outline
                                }
                        ),

                    colors =
                        ButtonDefaults
                            .outlinedButtonColors(

                                containerColor =
                                    if (selected) {

                                        MaterialTheme
                                            .colorScheme
                                            .primaryContainer

                                    } else {

                                        MaterialTheme
                                            .colorScheme
                                            .surface
                                    },

                                contentColor =
                                    if (selected) {

                                        MaterialTheme
                                            .colorScheme
                                            .primary

                                    } else {

                                        MaterialTheme
                                            .colorScheme
                                            .onSurfaceVariant
                                    }
                            )
                ) {

                    Text(
                        text = type,

                        style =
                            MaterialTheme
                                .typography
                                .labelMedium
                    )
                }
            }
        }


        Spacer(
            modifier =
                Modifier.height(14.dp)
        )


        // ------------------------------------------------
        // FECHA
        // ------------------------------------------------

        Text(
            text =
                "Fecha",

            style =
                MaterialTheme
                    .typography
                    .bodySmall,

            color =
                MaterialTheme
                    .colorScheme
                    .onSurface
        )


        Spacer(
            modifier =
                Modifier.height(6.dp)
        )


        OutlinedTextField(

            value =
                uiState.newActivityDate,

            onValueChange = {},

            modifier =
                Modifier.fillMaxWidth(),

            readOnly =
                true,

            singleLine =
                true,

            shape =
                RoundedCornerShape(12.dp),

            trailingIcon = {

                IconButton(
                    onClick = {
                        showDatePicker()
                    }
                ) {

                    Icon(
                        imageVector =
                            Icons.Default
                                .CalendarMonth,

                        contentDescription =
                            "Seleccionar fecha",

                        tint =
                            MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }
            }
        )


        Spacer(
            modifier =
                Modifier.height(24.dp)
        )


        // ------------------------------------------------
        // PRIORIZACIÓN
        // ------------------------------------------------

        Text(
            text =
                "Ayúdanos a priorizarla",

            style =
                MaterialTheme
                    .typography
                    .titleMedium,

            color =
                MaterialTheme
                    .colorScheme
                    .onBackground
        )


        Spacer(
            modifier =
                Modifier.height(10.dp)
        )


        // ------------------------------------------------
        // PESO
        // ------------------------------------------------

        Text(
            text =
                "Peso en la materia (Opcional)",

            style =
                MaterialTheme
                    .typography
                    .bodySmall,

            color =
                MaterialTheme
                    .colorScheme
                    .onSurface
        )


        Spacer(
            modifier =
                Modifier.height(6.dp)
        )


        OutlinedTextField(

            value =
                uiState.newActivityWeight,

            onValueChange = { value ->

                if (
                    value.isEmpty() ||
                    value.all {
                        it.isDigit()
                    }
                ) {

                    onWeightChange(
                        value
                    )
                }
            },

            modifier =
                Modifier.width(92.dp),

            suffix = {

                Text(
                    text = "%"
                )
            },

            keyboardOptions =
                KeyboardOptions(

                    keyboardType =
                        KeyboardType.Number
                ),

            singleLine =
                true,

            shape =
                RoundedCornerShape(12.dp)
        )


        Spacer(
            modifier =
                Modifier.height(16.dp)
        )


        // ------------------------------------------------
        // PREPARACIÓN
        // ------------------------------------------------

        Text(
            text =
                "¿Qué tan preparado/a te sientes?",

            style =
                MaterialTheme
                    .typography
                    .bodySmall,

            color =
                MaterialTheme
                    .colorScheme
                    .onSurface
        )


        Spacer(
            modifier =
                Modifier.height(10.dp)
        )


        Row(
            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically,

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Text(
                text =
                    "Muy\npoco",

                style =
                    MaterialTheme
                        .typography
                        .labelSmall,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )


            (1..5).forEach { value ->

                val selected =
                    uiState
                        .newActivityPreparation ==
                            value


                OutlinedButton(

                    onClick = {

                        onPreparationChange(
                            value
                        )
                    },

                    modifier =
                        Modifier.size(38.dp),

                    shape =
                        CircleShape,

                    contentPadding =
                        PaddingValues(0.dp),

                    border =
                        BorderStroke(

                            width = 1.dp,

                            color =
                                if (selected) {

                                    MaterialTheme
                                        .colorScheme
                                        .primary

                                } else {

                                    MaterialTheme
                                        .colorScheme
                                        .outline
                                }
                        ),

                    colors =
                        ButtonDefaults
                            .outlinedButtonColors(

                                containerColor =
                                    if (selected) {

                                        MaterialTheme
                                            .colorScheme
                                            .primaryContainer

                                    } else {

                                        MaterialTheme
                                            .colorScheme
                                            .surface
                                    },

                                contentColor =
                                    if (selected) {

                                        MaterialTheme
                                            .colorScheme
                                            .primary

                                    } else {

                                        MaterialTheme
                                            .colorScheme
                                            .onSurface
                                    }
                            )
                ) {

                    Text(
                        text =
                            value.toString(),

                        style =
                            MaterialTheme
                                .typography
                                .labelMedium
                    )
                }
            }


            Text(
                text =
                    "Muy\nbien",

                style =
                    MaterialTheme
                        .typography
                        .labelSmall,

                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }


        Spacer(
            modifier =
                Modifier.height(18.dp)
        )


        // ------------------------------------------------
        // TIEMPO ESTIMADO
        // ------------------------------------------------

        Text(
            text =
                "Tiempo estimado",

            style =
                MaterialTheme
                    .typography
                    .bodySmall,

            color =
                MaterialTheme
                    .colorScheme
                    .onSurface
        )


        Spacer(
            modifier =
                Modifier.height(6.dp)
        )


        Box(
            modifier =
                Modifier.fillMaxWidth()
        ) {

            OutlinedButton(

                onClick = {
                    timeMenuExpanded = true
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                shape =
                    RoundedCornerShape(12.dp),

                border =
                    BorderStroke(
                        width = 1.dp,
                        color =
                            MaterialTheme
                                .colorScheme
                                .outline
                    ),

                colors =
                    ButtonDefaults
                        .outlinedButtonColors(

                            containerColor =
                                MaterialTheme
                                    .colorScheme
                                    .surface,

                            contentColor =
                                MaterialTheme
                                    .colorScheme
                                    .onSurface
                        )
            ) {

                Row(
                    modifier =
                        Modifier.fillMaxWidth(),

                    horizontalArrangement =
                        Arrangement.SpaceBetween,

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Text(
                        text =
                            uiState
                                .newActivityEstimatedTime,

                        style =
                            MaterialTheme
                                .typography
                                .bodyMedium
                    )


                    Icon(
                        imageVector =
                            Icons.Default
                                .ArrowDropDown,

                        contentDescription =
                            "Seleccionar tiempo"
                    )
                }
            }


            DropdownMenu(

                expanded =
                    timeMenuExpanded,

                onDismissRequest = {
                    timeMenuExpanded = false
                },

                containerColor =
                    MaterialTheme
                        .colorScheme
                        .surface,

                tonalElevation =
                    0.dp
            ) {

                timeOptions.forEach { option ->

                    DropdownMenuItem(

                        text = {

                            Text(
                                text = option,

                                style =
                                    MaterialTheme
                                        .typography
                                        .bodyMedium
                            )
                        },

                        onClick = {

                            onEstimatedTimeChange(
                                option
                            )

                            timeMenuExpanded =
                                false
                        }
                    )
                }
            }
        }


        Spacer(
            modifier =
                Modifier.height(28.dp)
        )


        // ------------------------------------------------
        // GUARDAR
        // ------------------------------------------------

        Button(

            onClick =
                onSave,

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(52.dp),

            shape =
                RoundedCornerShape(12.dp),

            colors =
                ButtonDefaults
                    .buttonColors(

                        containerColor =
                            MaterialTheme
                                .colorScheme
                                .primary,

                        contentColor =
                            MaterialTheme
                                .colorScheme
                                .onPrimary
                    )
        ) {

            Text(
                text =
                    "Guardar actividad",

                style =
                    MaterialTheme
                        .typography
                        .labelLarge
            )
        }


        Spacer(
            modifier =
                Modifier.height(24.dp)
        )
    }
}