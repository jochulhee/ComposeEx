package com.one4u.composeex

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CheckboxColors
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.SwipeableState
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
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
        // TODO: replace with proper declarative non-android alternative when ready (b/158188351)
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
    fanColor: Color,
    pinColor: Color,
    modifier: Modifier = Modifier
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
    Canvas(modifier = modifier.size(48.dp).aspectRatio(1f)) {
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
}