package com.one4u.composeex

import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.os.Handler
import android.os.Looper
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CheckboxColors
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.SwipeableState
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TriStateCheckbox
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material.swipeable
import androidx.compose.runtime.State
import androidx.compose.runtime.Stable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.cos
import kotlin.math.sin

/*********************************************
 * Circle CheckBox
 * - Circle shape CheckBox
 * - unChecked checkMark 처리
 * */
private const val BoxInDuration = 50
private const val BoxOutDuration = 100
private const val CheckAnimationDuration = 100

private val CheckboxRippleRadius = 24.dp
private val CheckboxDefaultPadding = 2.dp
private val CheckboxSize = 20.dp
private val StrokeWidth = 3.dp
//private val RadiusSize = 2.dp

@Composable
fun CircleCheckBox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: CheckboxColors = CheckboxDefaults2.colors()
) {
    TriStateCheckbox2(
        state = ToggleableState(checked),
        onClick = if (onCheckedChange != null) { { onCheckedChange(!checked) } } else null,
        interactionSource = interactionSource,
        enabled = enabled,
        colors = colors,
        modifier = modifier
    )
}

@Composable
fun TriStateCheckbox2(
    state: ToggleableState,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: CheckboxColors = CheckboxDefaults2.colors()
) {
    val toggleableModifier =
        if (onClick != null) {
            Modifier.triStateToggleable(
                state = state,
                onClick = onClick,
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = CheckboxRippleRadius
                )
            )
        } else {
            Modifier
        }
    CheckboxImpl(
        enabled = enabled,
        value = state,
        modifier = modifier
            .then(
                if (onClick != null) {
                    Modifier.minimumInteractiveComponentSize()
                } else {
                    Modifier
                }
            )
            .then(toggleableModifier)
            .padding(CheckboxDefaultPadding),
        colors = colors
    )
}

object CheckboxDefaults2 {
    /**
     * Creates a [CheckboxColors] that will animate between the provided colors according to the
     * Material specification.
     *
     * @param checkedColor the color that will be used for the border and box when checked
     * @param uncheckedColor color that will be used for the border when unchecked
     * @param checkmarkColor color that will be used for the checkmark when checked
     * @param uncheckedCheckmarkColor color that will be used for the checkmark when unchecked
     * @param disabledColor color that will be used for the box and border when disabled
     * @param disabledIndeterminateColor color that will be used for the box and
     * border in a [TriStateCheckbox] when disabled AND in an [ToggleableState.Indeterminate] state.
     */
    @Composable
    fun colors(
        checkedColor: Color = MaterialTheme.colors.secondary,
        uncheckedColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
        checkmarkColor: Color = MaterialTheme.colors.surface,
        uncheckedCheckmarkColor: Color = MaterialTheme.colors.surface,//.copy(alpha = 0.6f),
        disabledColor: Color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
        disabledIndeterminateColor: Color = checkedColor.copy(alpha = ContentAlpha.disabled)
    ): CheckboxColors {
        return remember(
            checkedColor,
            uncheckedColor,
            checkmarkColor,
            uncheckedCheckmarkColor,
            disabledColor,
            disabledIndeterminateColor,
        ) {
            DefaultCheckboxColors(
                checkedBorderColor = checkedColor,
                checkedBoxColor = checkedColor,
                uncheckedBorderColor = uncheckedColor,
                uncheckedBoxColor = uncheckedColor,
                checkedCheckmarkColor = checkmarkColor,
                uncheckedCheckmarkColor = uncheckedCheckmarkColor,
                disabledCheckedBoxColor = disabledColor,
                disabledUncheckedBoxColor = disabledColor.copy(alpha = 0.6f),
                disabledIndeterminateBoxColor = disabledIndeterminateColor,
                disabledBorderColor = disabledColor,
                disabledIndeterminateBorderColor = disabledIndeterminateColor,
            )
        }
    }
}

@Stable
private class DefaultCheckboxColors(
    private val checkedCheckmarkColor: Color,
    private val uncheckedCheckmarkColor: Color,
    private val checkedBoxColor: Color,
    private val uncheckedBoxColor: Color,
    private val disabledCheckedBoxColor: Color,
    private val disabledUncheckedBoxColor: Color,
    private val disabledIndeterminateBoxColor: Color,
    private val checkedBorderColor: Color,
    private val uncheckedBorderColor: Color,
    private val disabledBorderColor: Color,
    private val disabledIndeterminateBorderColor: Color
) : CheckboxColors {
    @Composable
    override fun checkmarkColor(state: ToggleableState): State<Color> {
        val target = if (state == ToggleableState.On) {
            checkedCheckmarkColor
        } else {
            uncheckedCheckmarkColor
        }

        val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
        return animateColorAsState(target, tween(durationMillis = duration))
    }

    @Composable
    override fun boxColor(enabled: Boolean, state: ToggleableState): State<Color> {
        val target = if (enabled) {
            when (state) {
                ToggleableState.On, ToggleableState.Indeterminate -> checkedBoxColor
                ToggleableState.Off -> uncheckedBoxColor
            }
        } else {
            when (state) {
                ToggleableState.On -> disabledCheckedBoxColor
                ToggleableState.Indeterminate -> disabledIndeterminateBoxColor
                ToggleableState.Off -> disabledUncheckedBoxColor
            }
        }

        // If not enabled 'snap' to the disabled state, as there should be no animations between
        // enabled / disabled.
        return if (enabled) {
            val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
            animateColorAsState(target, tween(durationMillis = duration))
        } else {
            rememberUpdatedState(target)
        }
    }

    @Composable
    override fun borderColor(enabled: Boolean, state: ToggleableState): State<Color> {
        val target = if (enabled) {
            when (state) {
                ToggleableState.On, ToggleableState.Indeterminate -> checkedBorderColor
                ToggleableState.Off -> uncheckedBorderColor
            }
        } else {
            when (state) {
                ToggleableState.Indeterminate -> disabledIndeterminateBorderColor
                ToggleableState.On, ToggleableState.Off -> disabledBorderColor
            }
        }

        // If not enabled 'snap' to the disabled state, as there should be no animations between
        // enabled / disabled.
        return if (enabled) {
            val duration = if (state == ToggleableState.Off) BoxOutDuration else BoxInDuration
            animateColorAsState(target, tween(durationMillis = duration))
        } else {
            rememberUpdatedState(target)
        }
    }
}

@Composable
private fun CheckboxImpl(
    enabled: Boolean,
    value: ToggleableState,
    modifier: Modifier,
    colors: CheckboxColors
) {
    val transition = updateTransition(value, label = null)
    val checkDrawFraction by transition.animateFloat(
        transitionSpec = {
            when {
                initialState == ToggleableState.Off -> tween(CheckAnimationDuration)
                targetState == ToggleableState.Off -> snap(BoxOutDuration)
                else -> spring()
            }
        }, label = "null"
    ) {
        when (it) {
            ToggleableState.On -> 1f
            ToggleableState.Off -> 1f//when 0f, draw animation is showing
            ToggleableState.Indeterminate -> 0.5f
        }
    }

    val checkCenterGravitationShiftFraction by transition.animateFloat(
        transitionSpec = {
            when {
                initialState == ToggleableState.Off -> snap()
                targetState == ToggleableState.Off -> snap(BoxOutDuration)
                else -> tween(durationMillis = CheckAnimationDuration)
            }
        }, label = "null"
    ) {
        when (it) {
            ToggleableState.On -> 0f
            ToggleableState.Off -> 0f
            ToggleableState.Indeterminate -> 1f
        }
    }
    val checkCache = remember { CheckDrawingCache() }
    val checkColor by colors.checkmarkColor(value)
    val boxColor by colors.boxColor(enabled, value)
//    val borderColor by colors.borderColor(enabled, value)
    Canvas(
        modifier
            .wrapContentSize(Alignment.Center)
            .requiredSize(CheckboxSize)) {
        val strokeWidthPx = floor(StrokeWidth.toPx())
        drawCircle(
            boxColor = boxColor,
//            borderColor = borderColor,
//            strokeWidth = strokeWidthPx
        )
        drawCheck(
            checkColor = checkColor,
            checkFraction = checkDrawFraction,
            crossCenterGravitation = checkCenterGravitationShiftFraction,
            strokeWidthPx = strokeWidthPx,
            drawingCache = checkCache
        )
    }
}

private fun DrawScope.drawCircle(
    boxColor: Color,
//    borderColor: Color,
//    strokeWidth: Float
) {
//    val halfStrokeWidth = strokeWidth / 2.0f
//    val stroke = Stroke(strokeWidth)
    val checkboxSize = size.width * 0.7f
    drawCircle(
        color = boxColor,
        style = Fill,
        radius = checkboxSize
    )
}

private fun DrawScope.drawCheck(
    checkColor: Color,
    checkFraction: Float,
    crossCenterGravitation: Float,
    strokeWidthPx: Float,
    drawingCache: CheckDrawingCache
) {
    println("checkColor $checkColor")
    val stroke = Stroke(width = strokeWidthPx, cap = StrokeCap.Square)
    val width = size.width
    val checkCrossX = 0.4f
    val checkCrossY = 0.7f
    val leftX = 0.2f
    val leftY = 0.5f
    val rightX = 0.8f
    val rightY = 0.3f

    val gravitatedCrossX = lerp(checkCrossX, 0.5f, crossCenterGravitation)
    val gravitatedCrossY = lerp(checkCrossY, 0.5f, crossCenterGravitation)
    // gravitate only Y for end to achieve center line
    val gravitatedLeftY = lerp(leftY, 0.5f, crossCenterGravitation)
    val gravitatedRightY = lerp(rightY, 0.5f, crossCenterGravitation)

    with(drawingCache) {
        checkPath.reset()
        checkPath.moveTo(width * leftX, width * gravitatedLeftY)
        checkPath.lineTo(width * gravitatedCrossX, width * gravitatedCrossY)
        checkPath.lineTo(width * rightX, width * gravitatedRightY)
        pathMeasure.setPath(checkPath, false)
        pathToDraw.reset()
        pathMeasure.getSegment(
            0f, pathMeasure.length * checkFraction, pathToDraw, true
        )
    }
    drawPath(drawingCache.pathToDraw, checkColor, style = stroke)
}

@Immutable
private class CheckDrawingCache(
    val checkPath: Path = Path(),
    val pathMeasure: PathMeasure = PathMeasure(),
    val pathToDraw: Path = Path()
)
// -- Circle CheckBox





/*********************************************
 * Radio Button
 * - unChecked 상태의 Stroke 커스텀 처리를 위해 추가
* */
private const val RadioAnimationDuration = 100

private val RadioButtonRippleRadius = 24.dp
private val RadioButtonPadding = 2.dp
private val RadioButtonSize = 20.dp
private val RadioRadius = RadioButtonSize / 2
private val RadioButtonDotSize = 12.dp
private val RadioStrokeWidth = 2.dp
@Composable
fun RadioButton2(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: RadioButtonColors = RadioButtonDefaults.colors()
) {
    val dotRadius = animateDpAsState(
        targetValue = if (selected) RadioButtonDotSize / 2 else 0.dp,
        animationSpec = tween(durationMillis = RadioAnimationDuration)
    )
    val radioColor = colors.radioColor(enabled, selected)
    val selectableModifier =
        if (onClick != null) {
            Modifier.selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = RadioButtonRippleRadius
                )
            )
        } else {
            Modifier
        }
    Canvas(
        modifier
            .then(
                if (onClick != null) {
                    Modifier.minimumInteractiveComponentSize()
                } else {
                    Modifier
                }
            )
            .then(selectableModifier)
            .wrapContentSize(Alignment.Center)
            .padding(RadioButtonPadding)
            .requiredSize(RadioButtonSize)
    ) {
        // Draw the radio button
        val strokeWidth = RadioStrokeWidth.toPx()
        drawCircle(
            radioColor.value,
            RadioRadius.toPx() - strokeWidth / 2,
            style = if (dotRadius.value > 0.dp) Stroke(strokeWidth) else Stroke(strokeWidth/2)
        )
        if (dotRadius.value > 0.dp) {
            drawCircle(radioColor.value, dotRadius.value.toPx() - strokeWidth / 2, style = Fill)
        }
    }
}
// -- Radio Button

@Composable
inline fun Modifier.noRippleClickable(crossinline onClick: ()->Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}


/*********************************************
 * InnerThumb Switch
 * - Thumb is inside of track
 * */
internal val TrackWidth = 40.dp
internal val TrackStrokeWidth = 20.dp
internal val ThumbDiameter = 18.dp

//private val ThumbRippleRadius = 24.dp

private val DefaultSwitchPadding = 2.dp
private val SwitchWidth = TrackWidth
private val SwitchHeight = if(ThumbDiameter > TrackWidth) ThumbDiameter else TrackWidth
private val ThumbPathLength = TrackWidth - ThumbDiameter

private val AnimationSpec = TweenSpec<Float>(durationMillis = 100)

private val ThumbDefaultElevation = 1.dp
private val ThumbPressedElevation = 6.dp

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun InnerThumbSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SwitchColors = SwitchDefaults.colors(),
) {
    val minBound = 0f
    val maxBound = with(LocalDensity.current) { ThumbPathLength.toPx() }
    val swipeableState = rememberSwipeableStateFor(checked, onCheckedChange ?: {}, AnimationSpec)
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val toggleableModifier =
        if (onCheckedChange != null) {
            Modifier.toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                enabled = enabled,
                role = Role.Switch,
                interactionSource = interactionSource,
                indication = null
            )
        } else {
            Modifier
        }

    Box(
        modifier
            .then(
                if (onCheckedChange != null) {
                    Modifier.minimumInteractiveComponentSize()
                } else {
                    Modifier
                }
            )
            .then(toggleableModifier)
            .swipeable(
                state = swipeableState,
                anchors = mapOf(minBound to false, maxBound to true),
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal,
                enabled = enabled && onCheckedChange != null,
                reverseDirection = isRtl,
                interactionSource = interactionSource,
                resistance = null
            )
            .wrapContentSize(Alignment.Center)
            .padding(DefaultSwitchPadding)
            .requiredSize(SwitchWidth, SwitchHeight)
    ) {
        SwitchImpl(
            checked = checked,
            enabled = enabled,
            colors = colors,
            thumbValue = swipeableState.offset,
            interactionSource = interactionSource
        )
    }
}

@Composable
@ExperimentalMaterialApi
internal fun <T : Any> rememberSwipeableStateFor(
    value: T,
    onValueChange: (T) -> Unit,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec
): SwipeableState<T> {
    val swipeableState = remember {
        SwipeableState(
            initialValue = value,
            animationSpec = animationSpec,
            confirmStateChange = { true }
        )
    }
    val forceAnimationCheck = remember { mutableStateOf(false) }
    LaunchedEffect(value, forceAnimationCheck.value) {
        if (value != swipeableState.currentValue) {
            swipeableState.animateTo(value)
        }
    }
    DisposableEffect(swipeableState.currentValue) {
        if (value != swipeableState.currentValue) {
            onValueChange(swipeableState.currentValue)
            forceAnimationCheck.value = !forceAnimationCheck.value
        }
        onDispose { }
    }
    return swipeableState
}

@Composable
private fun BoxScope.SwitchImpl(
    checked: Boolean,
    enabled: Boolean,
    colors: SwitchColors,
    thumbValue: State<Float>,
    interactionSource: InteractionSource
) {
    val interactions = remember { mutableStateListOf<Interaction>() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> interactions.add(interaction)
                is PressInteraction.Release -> interactions.remove(interaction.press)
                is PressInteraction.Cancel -> interactions.remove(interaction.press)
                is DragInteraction.Start -> interactions.add(interaction)
                is DragInteraction.Stop -> interactions.remove(interaction.start)
                is DragInteraction.Cancel -> interactions.remove(interaction.start)
            }
        }
    }

    val hasInteraction = interactions.isNotEmpty()
    val elevation = if (hasInteraction) {
        ThumbPressedElevation
    } else {
        ThumbDefaultElevation
    }
    val trackColor by colors.trackColor(enabled, checked)
    Canvas(
        Modifier
            .align(Alignment.Center)
            .fillMaxSize()) {
        drawTrack(trackColor, TrackWidth.toPx(), TrackStrokeWidth.toPx())
    }
    val thumbColor by colors.thumbColor(enabled, checked)
    val elevationOverlay = LocalElevationOverlay.current
    val absoluteElevation = LocalAbsoluteElevation.current + elevation
    val resolvedThumbColor =
        if (thumbColor == MaterialTheme.colors.surface && elevationOverlay != null) {
            elevationOverlay.apply(thumbColor, absoluteElevation)
        } else {
            thumbColor
        }
    val offset = with(LocalDensity.current) { 1.dp.toPx() }.toInt()
    val thumbOffsetX = if (thumbValue.value.roundToInt() == 0) thumbValue.value.roundToInt() + offset
    else thumbValue.value.roundToInt() - offset
    Spacer(
        Modifier
            .align(Alignment.CenterStart)
            .offset { IntOffset(thumbOffsetX, 0) }
//            .indication(
//                interactionSource = interactionSource,
//                indication = rememberRipple(bounded = false, radius = ThumbRippleRadius)
//            )
            .requiredSize(ThumbDiameter)
            .shadow(elevation, CircleShape, clip = false)
            .background(resolvedThumbColor, CircleShape)
    )
}
private fun DrawScope.drawTrack(trackColor: Color, trackWidth: Float, strokeWidth: Float) {
    val strokeRadius = strokeWidth / 2
    drawLine(
        trackColor,
        Offset(strokeRadius, center.y),
        Offset(trackWidth - strokeRadius, center.y),
        strokeWidth,
        StrokeCap.Round
    )
}
// -- InnerThumb Switch


/**
 * Animation Object
 * - Moon to Sun
 * */
private fun DrawScope.drawMoonToSun(
    radius: Float,
    progress: Float,
    color: Color
) {
    val mainCircle = Path().apply {
        addOval(Rect(center, radius))
    }

    val initialOffset = center - Offset(radius * 2.3f, radius * 2.3f)

    val offset = (radius * 1.8f) * progress

    val subtractCircle = Path().apply {
        addOval(Rect(initialOffset + Offset(offset, offset), radius))
    }

    val moonToSunPath = Path().apply {
        op(mainCircle, subtractCircle, PathOperation.Difference)
    }

    drawPath(moonToSunPath, color)
}
private fun DrawScope.drawRays(
    color: Color,
    radius: Float,
    rayWidth: Float,
    rayLength: Float,
    alpha: Float = 1f,
    rayCount: Int = 8
) {
    for (i in 0 until rayCount) {
        val angle = (2 * Math.PI * i / rayCount).toFloat()

        val startX = center.x + radius * cos(angle)
        val startY = center.y + radius * sin(angle)

        val endX = center.x + (radius + rayLength) * cos(angle)
        val endY = center.y + (radius + rayLength) * sin(angle)

        drawLine(
            color = color,
            alpha = alpha,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            cap = StrokeCap.Round,
            strokeWidth = rayWidth
        )
    }
}

private fun DrawScope.drawStar(
    color: Color,
    centerOffset: Offset,
    radius: Float,
    alpha: Float
) {
    val leverage = radius * 0.1f

    val starPath = Path().apply {
        moveTo(centerOffset.x - radius, centerOffset.y)

        // -, -
        quadraticBezierTo(
            x1 = centerOffset.x - leverage, y1 = centerOffset.y - leverage,
            x2 = centerOffset.x, y2 = centerOffset.y - radius
        )

        // +, -
        quadraticBezierTo(
            x1 = centerOffset.x + leverage, y1 = centerOffset.y - leverage,
            x2 = centerOffset.x + radius, y2 = centerOffset.y
        )

        // +, +
        quadraticBezierTo(
            x1 = centerOffset.x + leverage, y1 = centerOffset.y + leverage,
            x2 = centerOffset.x, y2 = centerOffset.y + radius
        )

        // -, +
        quadraticBezierTo(
            x1 = centerOffset.x - leverage, y1 = centerOffset.y + leverage,
            x2 = centerOffset.x - radius, y2 = centerOffset.y
        )
    }

    drawPath(starPath, color, alpha)
}
// -- Animation Object

@Composable
private fun MoonToSunSwitcher(
    isMoon: Boolean,
    sunColor: Color,
    moonColor: Color,
    modifier: Modifier = Modifier,
    animationSpecProgress: AnimationSpec<Float> = tween(1000),
    animationSpecColor: AnimationSpec<Color> = tween(1000)
) {
    val progress by animateFloatAsState(
        targetValue = if (isMoon) 1f else 0f,
        animationSpec = animationSpecProgress,
        label = "Theme switcher progress"
    )
    val color by animateColorAsState(
        targetValue = if (isMoon) moonColor else sunColor,
        animationSpec = animationSpecColor,
        label = "Theme switcher color"
    )

    Canvas(modifier = modifier
        .size(48.dp)
        .aspectRatio(1f)
    ) {
        val width = size.width
        val height = size.height
        val baseRadius = width * 0.25f
        val extraRadius = width * 0.15f * progress
        val radius = baseRadius + extraRadius// radius : 50% ~ 90%,, 0~40%

        rotate(180f * (1 - progress)) {
            val raysProgress = if (progress < 0.5f) (progress / 0.85f) else 0f
            drawRays(
                color = sunColor,
                alpha = if (progress < 0.5f) 1f else 0f,
                radius = (radius * 1.5f) * (1f - raysProgress),
                rayWidth = radius * 0.3f,
                rayLength = radius * 0.2f
            )

            drawMoonToSun(radius, progress, color)
        }

        val starProgress = if (progress > 0.8f) ((progress - 0.8f) / 0.2f) else 0f// 80% 이상 부터 visibility 전환

        drawStar(
            color = moonColor,
            centerOffset = Offset(width * 0.4f, height * 0.4f),
            radius = (height * 0.05f) * starProgress,
            alpha =  starProgress
        )

        drawStar(
            color = moonColor,
            centerOffset = Offset(width * 0.2f, height * 0.2f),
            radius = (height * 0.1f) * starProgress,
            alpha =  starProgress
        )
    }
}

//
/**
 * rotating Fan
 * */
private fun DrawScope.drawPan(
    panColor: Color,
    radius: Float,
    fanCount: Int = 5,
    fanLength: Float
) {
    val panPath = Path().apply {
        for (i in 0 until fanCount) {
            val angle = (2 * Math.PI * i / fanCount).toFloat()

            // draw path point
            val startX = center.x + radius * cos(angle)
            val startY = center.y + radius * sin(angle)

            val posX1 = center.x + (radius + fanLength) * 0.9f * cos(angle)
            val posY1 = center.y + (radius + fanLength) * 0.9f * sin(angle)

            val lavX1 = center.x + (radius + fanLength) * (0.8f * cos(angle) + 0.3f * sin(angle))
            val lavY1 = center.y + (radius + fanLength) * (0.8f * sin(angle) - 0.3f * cos(angle))

            val posX2 = center.x + (radius + fanLength) * 0.65f * (cos(angle) - sin(angle))
            val posY2 = center.y + (radius + fanLength) * 0.65f * (cos(angle) + sin(angle))

            val lavX2 = center.x + (radius + fanLength) * (cos(angle) - 0.4f * sin(angle))
            val lavY2 = center.y + (radius + fanLength) * (0.4f * cos(angle) + sin(angle))

            val posX3 = center.x + (radius + fanLength) * 0.5f * (cos(angle) - sin(angle))
            val posY3 = center.y + (radius + fanLength) * 0.5f * (cos(angle) + sin(angle))

            val lavX3 = center.x + (radius + fanLength) * (0.5f * cos(angle) - 0.7f * sin(angle))
            val lavY3 = center.y + (radius + fanLength) * (0.7f * cos(angle) + 0.5f * sin(angle))

            val endX = center.x + radius * cos(angle + 0.5f)
            val endY = center.y + radius * sin(angle + 0.5f)

            val lavX4 = center.x + (radius + fanLength) * (0.5f * cos(angle) - 0.3f * sin(angle))
            val lavY4 = center.y + (radius + fanLength) * (0.3f * cos(angle) + 0.5f * sin(angle))

//            drawCircle(panColor, radius = 3f, center = Offset(startX, startY))
//            drawCircle(panColor, radius = 3f, center = Offset(posX1, posY1))
//            drawCircle(Color.Gray, radius = 2f, center = Offset(lavX1, lavY1))
//            drawCircle(panColor, radius = 3f, center = Offset(posX2, posY2))
//            drawCircle(Color.Gray, radius = 2f, center = Offset(lavX2, lavY2))
//            drawCircle(panColor, radius = 3f, center = Offset(posX3, posY3))
//            drawCircle(Color.Gray, radius = 2f, center = Offset(lavX3, lavY3))
//            drawCircle(Color.Red, radius = 2f, center = Offset(endX, endY))
//            drawCircle(Color.Red, radius = 2f, center = Offset(lavX4, lavY4))

            moveTo(startX, startY)

            quadraticBezierTo(
                x1 = lavX1, y1 = lavY1,
                x2 = posX1, y2 = posY1
            )

            quadraticBezierTo(
                x1 = lavX2, y1 = lavY2,
                x2 = posX2, y2 = posY2
            )

            quadraticBezierTo(
                x1 = lavX3, y1 = lavY3,
                x2 = posX3, y2 = posY3
            )

            quadraticBezierTo(
                x1 = lavX4, y1 = lavY4,
                x2 = endX, y2 = endY
            )
        }
    }
    drawPath(panPath, panColor, alpha = 1f)
}

private fun DrawScope.drawPin(
    pinColor: Color,
    pinRadius: Float
) {
    drawCircle(pinColor, radius = pinRadius/2.4f)
    drawCircle(color = pinColor, radius = pinRadius, style = Stroke(width = 2f))
}
@Composable
private fun RotatePan(
    modifier: Modifier = Modifier,
    fanColor: Color,
    pinColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition()
    val degree by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1800f,
        animationSpec = infiniteRepeatable(
            animation = tween(5 * 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Canvas(modifier = modifier
        .size(48.dp)
        .aspectRatio(1f)) {
        val width = size.width
        val pinRadius = width * 0.15f
        val fanLength = width * 0.35f
        rotate(degree) {
            drawPan(
                panColor = fanColor,
                radius = pinRadius,
//                panCount = 3,
                fanLength = fanLength
            )
        }

        drawPin(pinColor, pinRadius)
    }
}

/**
 * Degree gauge
 * */
enum class MarkLevel { // mark 세분화 정도
    Low,
    Mid,
    High
}
private fun DrawScope.drawGaugeBack(
    markColor: Color,
    markLevel: MarkLevel = MarkLevel.Mid,
    radius: Float,
    minDegree: Float = 0f,
    maxDegree: Float = 100f
) {
    var rayCount = ((maxDegree - minDegree) / 10).toInt()

    // draw low
    for (i in 0 until rayCount) {
        val angle = (2 * Math.PI * i / rayCount).toFloat()

        val startX = center.x + radius * cos(angle)
        val startY = center.y + radius * sin(angle)

        val endX = center.x + (radius + radius * 0.22f) * cos(angle)
        val endY = center.y + (radius + radius * 0.22f) * sin(angle)

        drawLine(
            color = markColor,
            alpha = 1f,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            cap = StrokeCap.Round,
            strokeWidth = 3f
        )
    }

    if (markLevel == MarkLevel.Low) return
    rayCount*=2

    // draw mid
    for (i in 0 until rayCount) {
        val angle = (2 * Math.PI * i / (rayCount)).toFloat()

        val startX = center.x + radius * cos(angle)
        val startY = center.y + radius * sin(angle)

        val endX = center.x + (radius + radius * 0.11f) * cos(angle)
        val endY = center.y + (radius + radius * 0.11f) * sin(angle)

        drawLine(
            color = markColor,
            alpha = 1f,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            cap = StrokeCap.Round,
            strokeWidth = 2f
        )
    }

    if (markLevel == MarkLevel.Mid) return
    rayCount*=5

    // draw high
    for (i in 0 until rayCount) {
        val angle = (2 * Math.PI * i / (rayCount)).toFloat()

        val startX = center.x + radius * cos(angle)
        val startY = center.y + radius * sin(angle)

        val endX = center.x + (radius + radius * 0.05f) * cos(angle)
        val endY = center.y + (radius + radius * 0.05f) * sin(angle)

        drawLine(
            color = markColor,
            alpha = 1f,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            cap = StrokeCap.Round,
            strokeWidth = 1f
        )
    }
}

private fun DrawScope.drawLevelPin(
    pinColor: Color,
    radius: Float
) {
    val angle = (1/2f * Math.PI).toFloat()
    // gauge pin
    val pinPath = Path().apply {
        val startX = center.x + 2f * sin(angle)
        val startY = center.y - 2f * cos(angle)

        val secondX = center.x + (radius) * cos(angle)
        val secondY = center.y + (radius) * sin(angle)

        val endX = center.x - 2f * sin(angle)
        val endY = center.y + 2f * cos(angle)

        moveTo(startX, startY)
        lineTo(secondX, secondY)
        lineTo(endX, endY)

    }

    drawPath(pinPath, pinColor, 1f)

    // center pin
    drawCircle(
        color = pinColor,
        radius = 3f
    )
    drawCircle(
        color = Color.White,
        radius = 2f
    )
}

private fun DrawScope.drawLabel(
    textMeasurer: TextMeasurer,
    labelColor: Color,
    labelSize: TextUnit,
    radius: Float,
    minDegree: Float = 0f,
    maxDegree: Float = 100f
) {
    // label 표기는 Mid level 핀에만 작성
    val labelCount = ((maxDegree - minDegree) / 10).toInt()

    for (i in 0 until labelCount) {
        val angle = (Math.PI * (4f * i + labelCount) / (2f * labelCount)).toFloat() // 3시 방향 0 degree 시작 -> rotate 90f(= + PI/2 )

        val offsetX = center.x + (radius + radius * 0.38f) * cos(angle) - 12f
        val offsetY = center.y + (radius + radius * 0.38f) * sin(angle) - 12f

        val minText = String.format("%02d", i + minDegree.toInt())
        drawText(
            textMeasurer = textMeasurer,
            text = if (i == 0) minText else "${i * 10 + minDegree.toInt()}",
            topLeft = Offset(
                x = offsetX,
                y = offsetY
            ),
            style = TextStyle(fontSize = labelSize, fontWeight = FontWeight.Light, color = labelColor)
        )
    }
}

@Composable
private fun Gauge(
    pinColor: Color,
    modifier: Modifier = Modifier,
    markColor: Color,
    markLevel: MarkLevel = MarkLevel.Mid,
    backColor: Color,
    minDegree: Float = 0f,
    maxDegree: Float = 100f,
    degree: Float = 50f,
    fontSize: TextUnit,
    fontColor: Color
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(modifier = modifier
        .size(140.dp)
        .aspectRatio(1f)
        .background(color = backColor)) {
        val radius = size.width / 3.2f
        rotate(90f) {
            drawGaugeBack(
                markColor = markColor,
                markLevel = markLevel,
                radius = radius,
                minDegree = minDegree,
                maxDegree = maxDegree,
            )
        }

        val d = (degree + minDegree) / (maxDegree - minDegree) * 360f
        rotate(d) {
            drawLevelPin(
                pinColor = pinColor,
                radius = radius)
        }

        drawLabel(
            textMeasurer = textMeasurer,
            labelColor = fontColor,
            labelSize = fontSize,
            radius = radius,
            minDegree = minDegree,
            maxDegree = maxDegree
        )

        if (degree >= maxDegree)
            drawText(
                textMeasurer = textMeasurer,
                text = "⟳x${(degree/maxDegree).toInt()}",
                topLeft = Offset(
                    x = size.width/2 - radius/5,
                    y = size.height/2 + radius/2
                ),
                style = TextStyle(fontSize = fontSize, fontWeight = FontWeight.Light, color = fontColor)
            )
    }
}

/**
 * Ring Progress
 *
 * */
private fun DrawScope.drawRingProgress(
    barWidth: Float,
    barColor: Color,
    progressColor: Color,
    radius: Float,
    degree: Float = 50f,
    maxDegree: Float = 100f
) {
    // draw arc, rounded cap
    val angle = degree / maxDegree * 360f
    val innerR = radius * (1 - barWidth / 2f)
    val path = Path().apply {
        addArc(oval = Rect(center, innerR), startAngleDegrees = 0f, sweepAngleDegrees = angle)
    }
    drawPath(
        path = path,
        color = progressColor,
        style = Stroke(
            width = radius * barWidth,
            cap = StrokeCap.Round
        )
    )

    // draw circle & arc, flat ring progress
//    val backCircle = Path().apply {
//        addOval(oval = Rect(center, radius))
//    }
//    drawPath(backCircle, barColor)
//
//    when {
//        degree == 0f -> {
//            // do nothing
//        }
//        degree % maxDegree == 0f -> {
//            val mainCircle = Path().apply {
//                addOval(oval = Rect(center, radius))
//            }
//            drawPath(mainCircle, progressColor)
//        }
//        else -> {
//            val angle = degree / maxDegree * 360f
//            val mainCircle = Path().apply {
//                moveTo(center.x, center.y)
//                arcTo(rect = Rect(center, radius), startAngleDegrees = 0f, sweepAngleDegrees = angle, forceMoveTo = false)
//            }
//            drawPath(mainCircle, progressColor)
//        }
//    }
//
//    val subtractCircle = Path().apply {
//        addOval(Rect(center, radius * (1f - barWidth)))
//    }
//    drawPath(subtractCircle, Color.White)
}



@Composable
private fun RingProgress(
    modifier: Modifier = Modifier,
    barWidth: Float = 0.1f, // 0% ~ 100%
    barColor: Color,
    progressColor: Color,
    degree: Float = 50f,
    maxDegree: Float = 100f,
    fontSize: TextUnit,
    fontColor: Color,
    animationSpecProgress: AnimationSpec<Float> = tween(1500, easing = FastOutSlowInEasing),
) {
    val textMeasurer = rememberTextMeasurer()
    var animate by remember{ mutableStateOf(false) }
    val progress by animateFloatAsState(
        targetValue = if (animate) degree else 0f,
        animationSpec = animationSpecProgress,
        label = "Theme switcher progress"
    )
    LaunchedEffect(key1 = Unit) {
//        delay(2.seconds)
        animate = true
    }
    Canvas(
        modifier = modifier
            .aspectRatio(1f)
            .background(color = Color.White)
    ) {
        val radius = size.width / 2.1f
        val text = String.format(Locale.KOREA, "%2.1f", progress/maxDegree*100f)

        rotate(270f) {
            drawRingProgress(
                barWidth = barWidth,
                barColor = barColor,
                progressColor = progressColor,
                radius = radius,
                degree = progress,
                maxDegree = maxDegree
            )
        }

        drawText(
            textMeasurer = textMeasurer,
            text = "$text\u0025" ,
            topLeft = Offset(
                x = size.width / 2 - ((text.length + 2) * fontSize.value)/2f,
                y = size.height / 2 - fontSize.value
            ),
            style = TextStyle(fontSize = fontSize, fontWeight = FontWeight.Medium, color = fontColor)
        )
    }

}


/**
 * Convex Style Border, Modifier
 * - https://medium.com/@kappdev/how-to-create-a-stunning-3d-border-in-jetpack-compose-e040fbb6b8de
 * */
data class ConvexStyle(
    val blur: Dp = 3.dp,
    val offset: Dp = 2.dp,
    val glareColor: Color = Color.White.copy(0.64f),
    val shadowColor: Color = Color.Black.copy(0.64f)
)

private fun DrawScope.drawConvexBorderShadow(
    outline: Outline,
    strokeWidth: Dp,
    blur: Dp,
    offsetX: Dp,
    offsetY: Dp,
    shadowColor: Color
) = drawIntoCanvas { canvas ->
    val shadowPaint = Paint().apply {
        this.style = PaintingStyle.Stroke
        this.color = shadowColor
        this.strokeWidth = strokeWidth.toPx()
    }

    canvas.saveLayer(size.toRect(), shadowPaint)

    val halfStrokeWidth = strokeWidth.toPx() / 2
    canvas.translate(halfStrokeWidth, halfStrokeWidth)
    canvas.drawOutline(outline, shadowPaint)

    // Apply blending mode and blur effect for the shadow
    shadowPaint.asFrameworkPaint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
    }
    shadowPaint.color = Color.Black

    canvas.translate(offsetX.toPx(), offsetY.toPx())
    canvas.drawOutline(outline = outline, shadowPaint)
    canvas.restore()
}

fun Modifier.convexBorder(
    color: Color,
    shape: Shape,
    strokeWidth:Dp = 8.dp,
    convexStyle: ConvexStyle = ConvexStyle()
) = this.drawWithContent {
    val adjustedSize = Size(size.width - strokeWidth.toPx(), size.height - strokeWidth.toPx())
    val outline = shape.createOutline(adjustedSize, layoutDirection, this)
    drawContent()

    val halfStrokeWidth = strokeWidth.toPx() / 2
    translate(halfStrokeWidth, halfStrokeWidth) {
        drawOutline(
            outline = outline,
            color = color,
            style = Stroke(width = strokeWidth.toPx())
        )
    }

    with(convexStyle) {
        drawConvexBorderShadow(outline, strokeWidth, blur, -offset, -offset, shadowColor)
        drawConvexBorderShadow(outline, strokeWidth, blur, offset, offset, glareColor)
    }
}


/**
 * Neu morphism style Object
 * */
fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = BlurMaskFilter(20f, Blur.NORMAL)
            }
            frameworkPaint.color = color.toArgb()


            val leftPx = offsetX.toPx() // 2
            val topPx = offsetY.toPx() // 2
            val rightPx = size.width + offsetX.toPx() // 2
            val bottomPx = size.height + offsetY.toPx() // 2

            canvas.drawRoundRect(
                left = leftPx,
                top = topPx,
                right = rightPx,
                bottom = bottomPx,
                paint = paint,
                radiusX = blurRadius.toPx(),
                radiusY = blurRadius.toPx()
            )
        }
    }
)

@Composable
fun NeuMorphismButton(
    modifier: Modifier = Modifier,
    cornerRadius: Dp,
    backColor: Color,
    text: String,
    onClick: (() -> Unit)
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val animationSpecOffset: AnimationSpec<Dp> = tween(400)
    val animationSpecAlpha: AnimationSpec<Float> = tween(400)

    val topOffset by animateDpAsState(
        targetValue = if (!isPressed) (-4).dp else (-1).dp,
        animationSpec = animationSpecOffset,
        label = "top shadow offset"
    )
    val botOffset by animateDpAsState(
        targetValue = if (!isPressed) 4.dp else 1.dp,
        animationSpec = animationSpecOffset,
        label = "bottom shadow offset"
    )
    val alpha by animateFloatAsState(
        targetValue = if (!isPressed) 0.8f else 0.05f,
        animationSpec = animationSpecAlpha,
        label = "shadow alpha"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(
                color = Color(0xFF666666).copy(alpha),
                offsetX = botOffset,
                offsetY = botOffset,
                blurRadius = cornerRadius
            )
            .shadow(
                color = Color(0xFFCCCCCC).copy(alpha),
                offsetX = topOffset,
                offsetY = topOffset,
                blurRadius = cornerRadius
            )
            .background(
                color = backColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .clip(shape = RoundedCornerShape(cornerRadius))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
//                indication = rememberRipple(),
//                indication = CustomIndication.apply { color = Color(0xEE556677).copy(0.1f) },
                onClick = onClick
            )
    ) {
//        Text(if (!isPressed) text else "Pressed")
        Text(text)
    }
}
object CustomIndication : Indication {
    var color: Color = Color.Gray.copy(0.1f)
    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val isPressed = interactionSource.collectIsPressedAsState()
        return remember(interactionSource) {
            DefaultDebugIndicationInstance(isPressed)
        }
    }
    private class DefaultDebugIndicationInstance(private val isPressed: State<Boolean>): IndicationInstance {
        override fun ContentDrawScope.drawIndication() {
            drawContent()
            if (isPressed.value) {
                drawRect(color = color, size = size)
            }
        }
    }
}

/**
 * Collapsing TopBar(NavigationBar)
 * */
@Composable
private fun CollapsingTopBar(

) {

}


/**
 * Animate Bottom TabBar
 * */
@Composable
private fun BottomTabBar(
    backColor: Color,
    height: Dp = 80.dp,
    cornerRadius :Dp = 10.dp,
    menus: ArrayList<BottomTabBarItem>,
    selectedTabListener: (idx: Int) -> Unit
) {
    val density = LocalDensity.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenWidthPx = with(density) { screenWidth.toPx().toInt() }
    var selectedTabIdx by remember{ mutableStateOf(1) }
    var animateSelectedTabIdx by remember{ mutableStateOf(1) }

    val animationSpec1: AnimationSpec<Float> = tween(durationMillis = 450, easing = CubicBezierEasing(0.3f, -0.05f, 0.7f, -0.5f))
    val animationSpec3: AnimationSpec<Float> = tween(durationMillis = 50, delayMillis = 350,  easing = CubicBezierEasing(0.3f, -0.05f, 0.7f, -0.5f))
    val animationSpec4: AnimationSpec<Float> = tween(durationMillis = 50, easing = LinearEasing)

    val targetOffsetX = screenWidthPx * ((animateSelectedTabIdx * 4f) + 3) / ((menus.size * 2f + 1f) * 2f)
    val animateTargetOffsetX by animateFloatAsState(
        targetValue = targetOffsetX,
        animationSpec = animationSpec3,
        label = ""
    )

    var pressed by remember { mutableStateOf(false) }
    var secondEffect by remember { mutableStateOf(false) }

    val backCircleRadius  = height.value * 1.3f
    val backCircleOffsetY by animateFloatAsState(
        targetValue = if (!pressed) height.value * 0.15f else - height.value * 1.5f,
        animationSpec = if (!pressed) animationSpec4 else animationSpec3,
        label = ""
    )

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(height)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // draw path.. and circle
            val corner = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
            val roundRectPath = Path().apply {
                addRoundRect(
                    roundRect = RoundRect(
                        rect = Rect(
                            offset = Offset(0f, size.height * 0.24f),
                            size = Size(size.width, size.height * 0.76f)
                        ),
                        topLeft = corner,
                        topRight = corner,
                        bottomLeft = corner,
                        bottomRight = corner
                    )
                )
            }
            val subtractCircle = Path().apply {
                addOval(oval = Rect(Offset(animateTargetOffsetX, size.height * 0.35f), backCircleRadius))
            }
            val background = Path().apply {
                op(roundRectPath, subtractCircle, PathOperation.Difference)
            }
            drawPath(background, backColor)
        }
        for (idx in menus.indices) {
            val frontCircleRadius by animateFloatAsState(
                targetValue = if (idx == animateSelectedTabIdx) height.value * 1.0f else height.value * 0.35f,
                animationSpec = animationSpec1,
                label = ""
            ) {
                pressed = false
                secondEffect = true
            }

            Canvas(modifier = Modifier.fillMaxSize()) {
                val offsetX = screenWidthPx * ((idx * 4f) + 3) / ((menus.size * 2f + 1f) * 2f)
                drawCircle(
                    backColor,
                    radius = frontCircleRadius,
                    center = Offset(offsetX, height.toPx() * 0.37f)
                )
            }
        }

//        val colors = arrayListOf(Color(0x99FF0000), Color(0x9900FF00), Color(0x990000FF), Color(0x99333333))
        Row(modifier = Modifier
            .height(height = height)
            .fillMaxSize()
            , verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f))
            for (idx in menus.indices) {
                if (idx > 5) break
                Column(modifier = Modifier
//                    .background(color = colors[idx])
                    .weight(1f)
                    .noRippleClickable {
                        pressed = true
                        Handler(Looper.getMainLooper()).postDelayed({
                            selectedTabListener.invoke(idx)
                            selectedTabIdx = idx
                        }, 400)
                        animateSelectedTabIdx = idx
                    }
                    , horizontalAlignment = Alignment.CenterHorizontally) {
                    AnimatedVisibility(
                        visible = idx == selectedTabIdx,
                        enter = fadeIn(initialAlpha = 0.5f) + slideInVertically(initialOffsetY = { height -> (height * 0.13f).toInt() }) + scaleIn(initialScale = 0.6f),
                        exit = fadeOut(targetAlpha = 0.5f) + slideOutVertically(targetOffsetY = { height -> (height * 0.13f).toInt() }) + scaleOut(targetScale = 0.6f)
                    ) {
                        Column {
                            Icon(
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .fillMaxHeight(0.7f),
                                painter = menus[idx].painter,
                                contentDescription = menus[idx].title,
                                tint = Color(0xFF3E74FF)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
//                    AnimatedVisibility(
//                        visible = idx != selectedTabIdx,
//                        enter = fadeIn(),
//                        exit = fadeOut()
//                    ) {
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                            Spacer(modifier = Modifier.height(20.dp))
//                            Icon(
//                                painter = menus[idx].painter,
//                                contentDescription = menus[idx].title,
//                                tint = colorResource(id = R.color.cg_70)
//                            )
//                            Text(
//                                text = menus[idx].title,
//                                color = colorResource(id = R.color.cg_70),
//                                fontSize = 12.sp
//                            )
//                        }
//                    }
                    if (idx == selectedTabIdx) {
//                        Icon(
//                            modifier = Modifier
//                                .fillMaxWidth(0.7f)
//                                .fillMaxHeight(0.7f),
//                            painter = menus[idx].painter,
//                            contentDescription = menus[idx].title,
//                            tint = Color(0xFF3E74FF)
//                        )
//                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        Spacer(modifier = Modifier.height(20.dp))
                        Icon(
                            painter = menus[idx].painter,
                            contentDescription = menus[idx].title,
                            tint = colorResource(id = R.color.cg_70)
                        )
                        Text(
                            text = menus[idx].title,
                            color = colorResource(id = R.color.cg_70),
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
data class BottomTabBarItem(
    val painter: Painter,
    val title: String
)


/**
 * CountdownSnackbar
 * */
@Composable
fun CountdownSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    durationInSeconds: Int = 5,
    actionOnNewLine: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    containerColor: Color = SnackbarDefaults.backgroundColor,
    contentColor: Color = MaterialTheme.colors.surface,
    actionColor: Color = SnackbarDefaults.primaryActionColor,
//    actionContentColor: Color = SnackbarDefaults.actionContentColor,
//    dismissActionContentColor: Color = SnackbarDefaults.dismissActionContentColor,
) {
    var animateDone by remember { mutableStateOf(false) }
    val totalDuration = remember(durationInSeconds) { durationInSeconds * 1000 }
    val animateRemainTime by animateIntAsState(
        targetValue = if (!animateDone) totalDuration else 0,
        animationSpec = tween(durationMillis = totalDuration, easing = LinearEasing),
        label = ""
    ) {
//        animateDone = false
        snackbarData.dismiss()
    }

    // Define the action button if an action label is provided
    val actionLabel = snackbarData.actionLabel
    val contentMessage = snackbarData.message
    val actionComposable: (@Composable () -> Unit)? = if (actionLabel != null) {
        @Composable {
            TextButton(
                colors = ButtonDefaults.textButtonColors(contentColor = actionColor),
                onClick = { snackbarData.performAction() },
                content = { Text(actionLabel) }
            )
        }
    } else {
        null
    }

    // Define the dismiss button if the snackbar includes a dismiss action
//    val dismissActionComposable: (@Composable () -> Unit)? = if (snackBarData.) {
//        @Composable {
//            IconButton(
//                onClick = { snackBarData.dismiss() },
//                content = {
//                    Icon(Icons.Rounded.Close, null)
//                }
//            )
//        }
//    } else {
//        null
//    }

    Snackbar(
        modifier = modifier.padding(12.dp),
        action = actionComposable,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        backgroundColor = containerColor,
        contentColor = contentColor,
    ) {//content
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SnackbarCountdown(
                timerProgress = animateRemainTime.toFloat() / totalDuration.toFloat(),
                secondsRemaining = ceil(animateRemainTime.toDouble() / 1000).toInt(),
                color = contentColor
            )
            Text(contentMessage)
        }
    }

    // start animating
    LaunchedEffect(key1 = Unit) {
        animateDone = true
    }
}
@Composable
private fun SnackbarCountdown(
    timerProgress: Float,
    secondsRemaining: Int,
    color: Color
) {
    Box(
        modifier = Modifier.size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val strokeStyle = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
            drawCircle(
                color = color.copy(alpha = 0.12f),
                style = strokeStyle
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = (-360f * timerProgress),
                useCenter = false,
                style = strokeStyle
            )
        }

        Text(
            text = secondsRemaining.toString(),
            style = LocalTextStyle.current.copy(
                fontSize = 14.sp,
                color = color
            )
        )
    }
}

@Preview
@Composable
private fun uiPreview() {
    // 1.
//    var checkState by remember{ mutableStateOf(false) }
//    CircleCheckBox(
//        modifier = Modifier,
//        checked = checkState,
//        onCheckedChange = {
//            checkState = it
//        },
//        colors = CheckboxDefaults2.colors(
//            checkedColor = colorResource(R.color.bl_100),
//            uncheckedColor = colorResource(R.color.cg_30),
//            checkmarkColor = colorResource(R.color.wt_100),
//            uncheckedCheckmarkColor = colorResource(R.color.wt_100)
//        )
//    )


    // 2.
//    var checkState by remember{ mutableStateOf(false) }
//    RadioButton2(
//        modifier = Modifier
//            .padding(5.dp)
//            .size(18.dp),
//        selected = checkState,
//        onClick = {
//            checkState = !checkState
//        },
//        colors = RadioButtonDefaults.colors(
//            selectedColor = colorResource(id = R.color.bl_100),
//            unselectedColor = colorResource(id = R.color.cg_50)
//        )
//    )


    // 3.
//    var checkState by remember{ mutableStateOf(false) }
//    InnerThumbSwitch(checked = checkState,
//        onCheckedChange = {
//            checkState = it
//        },
//        colors = SwitchDefaults.colors(
//            checkedThumbColor = colorResource(R.color.wt_100),
//            checkedTrackColor = colorResource(R.color.bl_100),
//            checkedTrackAlpha = 1f,
//            uncheckedThumbColor = colorResource(R.color.wt_100),
//            uncheckedTrackColor = colorResource(R.color.cg_40),
//            uncheckedTrackAlpha = 1f
//        )
//    )


    // 4.
//    val infiniteTransition = rememberInfiniteTransition()
//    val isMoon by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 1f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(2000, easing = LinearEasing),
//            repeatMode = RepeatMode.Reverse
//        )
//    )
//    MoonToSunSwitcher(isMoon = isMoon > 0.5f, sunColor = Color(0xFFFFB136), moonColor = Color(0xFFFFED72))


    // 5.
//    RotatePan(
//        fanColor = Color(0XFF000000),
//        pinColor = Color(0XFF000000)
//    )

    // 6.
//    Gauge(
//        pinColor = Color(0xFF333333),
//        markColor = Color(0xFF000000),
//        backColor = Color(0xFFFFFFFF),
//        markLevel = MarkLevel.High,
//        minDegree = 0f,
//        maxDegree = 120f,
//        degree = 1880f,
//        fontSize = 8.sp,
//        fontColor = Color(0xFF333333),
//        modifier = Modifier)

    // 7.
//    RingProgress(
//        modifier = Modifier.size(100.dp),
//        barWidth = 0.3f,
//        barColor = Color(0xFF333333),
//        progressColor = Color(0xFF000000),
//        degree = 12f,
//        maxDegree = 120f,
//        fontSize = 9.sp,
//        fontColor = Color(0xFF000000)
//    )

    // 8. Modifier - convexBorder
//    var text by remember { mutableStateOf("") }
//    BasicTextField(
//        value = text,
//        onValueChange = {
//            text = it
//        },
//        singleLine = true,
//        keyboardOptions = KeyboardOptions(
//            capitalization = KeyboardCapitalization.Sentences,
//            imeAction = ImeAction.Search
//        ),
//        textStyle = LocalTextStyle.current.copy(
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Medium
//        ),
//        decorationBox = { innerTextField ->
//            Row(
//                modifier = Modifier
//                    .size(300.dp, 60.dp)
//                    .background(Color(0XFFAAAAAA), CircleShape)
//                    .convexBorder(
//                        color = Color(0XFFCCCCDD),
//                        shape = CircleShape,
//                        convexStyle = ConvexStyle(
////                            shadowColor = Color(0xFFDD0000).copy(0.7f)
//                        )
//                    )
//                    .padding(horizontal = 20.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.Rounded.Search,
//                    contentDescription = null
//                )
//                Box {
//                    if (text.isEmpty()) {
//                        Text(
//                            text = "Search...",
//                            style = LocalTextStyle.current.copy(Color(0xFF242424))
//                        )
//                    }
//                    innerTextField()
//                }
//            }
//        }
//    )

    // 9.
//    Column(
//        modifier = Modifier
//            .size(280.dp, 400.dp)
//            .background(Color(0xFFAAAAAA))
//            .padding(40.dp)
//    ) {
//        Row {
//            NeuMorphismButton(
//                modifier = Modifier
//                    .size(50.dp, 50.dp),
//                cornerRadius = 10.dp,
//                backColor = Color(0xFFAAAAAA),
//                text= "Btn"
//            ) {
//
//            }
//
//            Spacer(modifier = Modifier.width(25.dp))
//
//            NeuMorphismButton(
//                modifier = Modifier
//                    .size(50.dp, 50.dp),
//                cornerRadius = 10.dp,
//                backColor = Color(0xFFAAAAAA),
//                text= "Btn"
//            ) {
//
//            }
//
//            Spacer(modifier = Modifier.width(25.dp))
//
//            NeuMorphismButton(
//                modifier = Modifier
//                    .size(50.dp, 50.dp),
//                cornerRadius = 10.dp,
//                backColor = Color(0xFFAAAAAA),
//                text= "Btn"
//            ) {
//
//            }
//        }
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        NeuMorphismButton(
//            modifier = Modifier
//                .size(200.dp, 70.dp),
//            cornerRadius = 20.dp,
//            backColor = Color(0xFFAAAAAA),
//            text= "Button1"
//        ) {
//
//        }
//        Spacer(modifier = Modifier.height(30.dp))
//
//        Row {
//            NeuMorphismButton(
//                modifier = Modifier
//                    .size(90.dp, 45.dp),
//                cornerRadius = 1.dp,
//                backColor = Color(0xFFAAAAAA),
//                text= "Button2"
//            ) {
//
//            }
//
//            Spacer(modifier = Modifier.width(20.dp))
//
//            NeuMorphismButton(
//                modifier = Modifier
//                    .size(90.dp, 45.dp),
//                cornerRadius = 1.dp,
//                backColor = Color(0xFFAAAAAA),
//                text= "Button3"
//            ) {
//
//            }
//        }
//
//        Spacer(modifier = Modifier.height(30.dp))
//
//        Row {
//            NeuMorphismButton(
//                modifier = Modifier
//                    .size(50.dp, 50.dp),
//                cornerRadius = 25.dp,
//                backColor = Color(0xFFAAAAAA),
//                text= "Btn"
//            ) {
//
//            }
//
//            Spacer(modifier = Modifier.width(25.dp))
//
//            NeuMorphismButton(
//                modifier = Modifier
//                    .size(50.dp, 50.dp),
//                cornerRadius = 25.dp,
//                backColor = Color(0xFFAAAAAA),
//                text= "Btn"
//            ) {
//
//            }
//
//            Spacer(modifier = Modifier.width(25.dp))
//
//            NeuMorphismButton(
//                modifier = Modifier
//                    .size(50.dp, 50.dp),
//                cornerRadius = 25.dp,
//                backColor = Color(0xFFAAAAAA),
//                text= "Btn"
//            ) {
//
//            }
//        }
//    }

    // 10. Collapsing TopBar
    CollapsingTopBar()

    // 11. Animate in Bottom TapBar
//    Box(modifier = Modifier
//        .fillMaxWidth()
//        .height(200.dp), contentAlignment = Alignment.BottomCenter) {
//        BottomTabBar(
//            backColor = Color.White,
//            height = 92.dp,
//            cornerRadius = 35.dp,
//            menus = arrayListOf(
//                BottomTabBarItem(painter = painterResource(id = R.drawable.icn_main_cam), title = "Camera"),
//                BottomTabBarItem(painter = painterResource(id = R.drawable.icn_main_home), title = "Home"),
//                BottomTabBarItem(painter = painterResource(id = R.drawable.icn_main_search), title = "Search")
//            )
//        ) { selectedIdx ->
//            when (selectedIdx) {
//                0 -> {
//                    // camera
//                }
//
//                1 -> {
//                    // main
//                }
//
//                2 -> {
//                    // search
//                }
//            }
//        }
//    }

    // 12. Snackbar Countdown
    Box(Modifier.fillMaxSize()) {
//        val results = remember { mutableStateListOf<String>() }
//        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = Color.LightGray.copy(0.5f))
//                .padding(10.dp)
//        ) {
//            items(results.size) {idx ->
//                Text(results[idx])
//            }
//        }

        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                // show snackbar and do something(process...)
                scope.launch {
                    snackbarHostState.currentSnackbarData?.performAction()

                    val result = snackbarHostState.showSnackbar(
                        message = "Something will be done soon.",
                        actionLabel = "Undo",
                        duration = SnackbarDuration.Indefinite// countdown
                    )

                    when(result) {
                        SnackbarResult.Dismissed -> {
                            // do something now.
//                            Toast.makeText(context, "done", Toast.LENGTH_SHORT).show()
//                            results.add("Done")
                        }
                        SnackbarResult.ActionPerformed -> {
                            // canceled
//                            Toast.makeText(context, "canceled", Toast.LENGTH_SHORT).show()
//                            results.add("Canceled")
                        }
                    }
                }
            }
        ) {
            Text("Do Something")
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) { data ->
            CountdownSnackbar(
                snackbarData = data,
                durationInSeconds = 5
            )
        }
    }
}
