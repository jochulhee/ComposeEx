@file:OptIn(ExperimentalMaterialApi::class)

package com.one4u.composeex

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import android.graphics.BlurMaskFilter.Blur
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.GradientDrawable
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CheckboxColors
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
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
import androidx.compose.material.TopAppBar
import androidx.compose.material.TriStateCheckbox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material.swipeable
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import kotlin.math.absoluteValue

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
                indication = ripple(
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
        animationSpec = tween(durationMillis = RadioAnimationDuration), label = ""
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
                indication = ripple(
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

@SuppressLint("ModifierFactoryUnreferencedReceiver")
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
internal fun <Boolean : Any> rememberSwipeableStateFor(
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec
): SwipeableState<Boolean> {
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
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val degree by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1800f,
        animationSpec = infiniteRepeatable(
            animation = tween(5 * 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
        , label = ""
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

        val minText = String.format(Locale.KOREA, "%02d", i + minDegree.toInt())
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
        maskFilter = BlurMaskFilter(blur.toPx(), Blur.NORMAL)
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
                onClick = onClick
            )
    ) {
//        Text(if (!isPressed) text else "Pressed")
        Text(text)
    }
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

/**
 * 고유 event 를 handling 하는 SampleView - Tile
 * */
@Composable
fun Tile(modifier: Modifier = Modifier) {
    val repeatingAnimation = rememberInfiniteTransition(label = "")

    val float = repeatingAnimation.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(repeatMode = RepeatMode.Reverse,
            animation = tween(5000)), label = ""
    )
    Box(modifier = modifier
        .size(100.dp)
        .background(Color.Gray, RoundedCornerShape(8.dp))){
        Text("Tile 1 ${float.value.roundToInt()}",
            modifier = Modifier.align(Alignment.Center))
    }
}

/**
 * Swipe Example with email list
 * */
data class EmailMessage(
    val sender: String,
    val message: String,
    val time : Date
) {
    fun getTime() : String {
        val dateFormat = "yyyy-mm-dd HH:mm"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.KOREA)
        return simpleDateFormat.format(time)
    }
}
// EmailMessageCard
@Composable
fun EmailMessageCard(emailMessage: EmailMessage) {
    ListItem(
        modifier = Modifier.clip(RoundedCornerShape(8.dp)),
        overlineContent = { },
        headlineContent = {
            Row {
                Text(
                    emailMessage.sender,
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    emailMessage.getTime(),
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                    )
                )
            }
        },
        supportingContent = {
            Text(
                emailMessage.message,
                style = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                )
            )
        },
        leadingContent = {
            Icon(
                Icons.Filled.Person,
                contentDescription = "person icon",
                Modifier
                    .clip(CircleShape)
                    .background(Color.Gray.copy(0.6f))
                    .padding(10.dp)
            )
        },
        trailingContent = { }
    )
}
// DismissBackground
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> Color(0xFFFF1744)// >>>
        SwipeToDismissBoxValue.EndToStart -> Color(0xFF1DE9B6)// <<<
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color, shape = RoundedCornerShape(8.dp))
            .padding(20.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "delete"
        )
        Spacer(modifier = Modifier)
        Icon(
            Icons.Default.Done,
            contentDescription = "Archive"
        )
    }
}
// EmailItem
@Composable
fun EmailItem(
    emailMessage: EmailMessage,
    modifier: Modifier = Modifier,
    onRemove: (EmailMessage) -> Unit
) {
//    val context = LocalContext.current
    val currentItem by rememberUpdatedState(emailMessage)
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when(it) {
                // swipe action
                SwipeToDismissBoxValue.StartToEnd -> {
                    onRemove(currentItem)
//                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    onRemove(currentItem)
//                    Toast.makeText(context, "Item archived", Toast.LENGTH_SHORT).show()
                }
                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        // positional threshold of 25%
        positionalThreshold = { it * .25f }
    )

    SwipeToDismissBox(// swipe control view
        state = dismissState,
        modifier = modifier.padding(5.dp),
        backgroundContent = { DismissBackground(dismissState) },
        content = { EmailMessageCard(emailMessage) }
    )
}


/**
 * PaginatedLazyColumn
 * */
@Composable
fun PaginatedLazyColumn(
    items: PersistentList<String>,  // Using PersistentList for efficient state management
    loadMoreItems: () -> Unit,  // Function to load more items
    listState: LazyListState,   // Track the scroll state of the LazyColumn
    buffer: Int = 2,            // Buffer to load more items when we get near the end
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val totalItemCount = listState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex: Int = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            (lastVisibleItemIndex >= (totalItemCount - buffer)) && !isLoading
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { shouldLoadMore.value }
//            .distinctUntilChanged()
            .filter { it }  // Ensure that we load more items only when needed
            .collect {
                loadMoreItems()
            }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
        , state = listState
    ) {
        // Render each item in the list using a unique key
        itemsIndexed(items, key = { _, item -> item}) { index, item ->
            Text(text = item, modifier = Modifier.padding(8.dp))
        }
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                    , contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

/**
 * Modifier extensions
 * */
private fun Modifier.colorFilter(colorFilter: ColorFilter): Modifier {
    return this.drawWithCache {
        val graphicsLayer = obtainGraphicsLayer()
        graphicsLayer.apply {
            record {
                drawContent()
            }
            this.colorFilter = colorFilter
        }
        onDrawWithContent {
            drawLayer(graphicsLayer)
        }
    }
}
private fun Modifier.blendMode(blendMode: BlendMode): Modifier {// background 와의 효과 적용
    return this.drawWithCache {
        val graphicsLayer = obtainGraphicsLayer()
        graphicsLayer.apply {
            record {
                drawContent()
            }
            this.blendMode = blendMode
        }
        onDrawWithContent {
            drawLayer(graphicsLayer)
        }
    }
}

@Composable
private fun PagerSampleItem(page: Int) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .background(
            brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                colors = listOf(
                    Color.Black,
                    Color.Gray,
                    Color.White,
                    Color.LightGray,
                    Color.White,
                    Color.Gray,
                    Color.Black
                )
            )
        ),
        contentAlignment = Alignment.Center
    ) {
        Text("page : $page", fontSize = 26.sp)
    }
}

/**
 * Parallax Pager
 * */
private val backgrounds = listOf(R.drawable.background_1, R.drawable.background_2, R.drawable.background_3, R.drawable.background_4)
@Composable
private fun GradientOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                androidx.compose.ui.graphics.Brush.verticalGradient(
                    listOf(Color.Black.copy(alpha = 0.7f), Color.Black.copy(alpha = 0.2f), Color.Transparent),
                    startY = 0f,
                    endY = 600f
                )
            )
    )
}

@Composable
private fun BackgroundImagePager(state: PagerState) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = state
    ) { currentPage ->

        Box(Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter) {
            // background
            Image(
                painter = painterResource(id = backgrounds[currentPage]),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            // gradient cover
            GradientOverlay()

            val currentPageOffset = calculatePageOffset(state, currentPage)
            val cardTranslationX = lerp(800f, 0f, 1f - currentPageOffset)

            // top Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .background(color = Color.White, shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .border(width = 3.dp, color = Color.White, shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            ) {
                Image(
                    painter = painterResource(id = backgrounds[currentPage]),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                        .graphicsLayer {
                            translationX = cardTranslationX
                        }
                )
            }
        }
    }
}
fun calculatePageOffset(state: PagerState, currentPage: Int): Float {
    return (state.currentPage + state.currentPageOffsetFraction - currentPage).coerceIn(-1f, 1f)
}
@Composable
fun ParallaxImagePager(paddingValues: PaddingValues) {
    val backgroundPagerState = rememberPagerState(pageCount = { backgrounds.size })
//    val imgCardPagerState = rememberPagerState(pageCount = { backgrounds.size })
//
//    // Derived state to track scrolling status
//    val scrollingFollowingPair by remember {
//        derivedStateOf {
//            when {
//                backgroundPagerState.isScrollInProgress -> backgroundPagerState to imgCardPagerState
//                imgCardPagerState.isScrollInProgress -> imgCardPagerState to backgroundPagerState
//                else -> null
//            }
//        }
//    }
//
//    // Synchronizing scrolling of two pagers
//    LaunchedEffect(scrollingFollowingPair) {
//        scrollingFollowingPair?.let { (scrollingState, followingState) ->
//            snapshotFlow { scrollingState.currentPage + scrollingState.currentPageOffsetFraction }
//                .collect { pagePart ->
//                    val (page, offset) = java.math.BigDecimal.valueOf(pagePart.toDouble())
//                        .divideAndRemainder(java.math.BigDecimal.ONE)
//                        .let { it[0].toInt() to it[1].toFloat() }
//
//                    followingState.requestScrollToPage(page, offset)
//                }
//        }
//    }

    // Layout for both pagers
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.TopCenter
    ) {
        BackgroundImagePager(backgroundPagerState)
    }
}

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Preview
@Composable
private fun UiPreview() {
    // 1.
/*
    var checkState by remember{ mutableStateOf(false) }
    CircleCheckBox(
        modifier = Modifier,
        checked = checkState,
        onCheckedChange = {
            checkState = it
        },
        colors = CheckboxDefaults2.colors(
            checkedColor = colorResource(R.color.bl_100),
            uncheckedColor = colorResource(R.color.cg_30),
            checkmarkColor = colorResource(R.color.wt_100),
            uncheckedCheckmarkColor = colorResource(R.color.wt_100)
        )
    )
*/

    // 2.
/*
    var checkState by remember{ mutableStateOf(false) }
    RadioButton2(
        modifier = Modifier
            .padding(5.dp)
            .size(18.dp),
        selected = checkState,
        onClick = {
            checkState = !checkState
        },
        colors = RadioButtonDefaults.colors(
            selectedColor = colorResource(id = R.color.bl_100),
            unselectedColor = colorResource(id = R.color.cg_50)
        )
    )
*/

    // 3.
/*
    var checkState by remember{ mutableStateOf(false) }
    InnerThumbSwitch(checked = checkState,
        onCheckedChange = {
            checkState = it
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = colorResource(R.color.wt_100),
            checkedTrackColor = colorResource(R.color.bl_100),
            checkedTrackAlpha = 1f,
            uncheckedThumbColor = colorResource(R.color.wt_100),
            uncheckedTrackColor = colorResource(R.color.cg_40),
            uncheckedTrackAlpha = 1f
        )
    )
*/

    // 4.
/*
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val isMoon by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    MoonToSunSwitcher(isMoon = isMoon > 0.5f, sunColor = Color(0xFFFFB136), moonColor = Color(0xFFFFED72))
*/

    // 5.
/*
    RotatePan(
        fanColor = Color(0XFF000000),
        pinColor = Color(0XFF000000)
    )
*/

    // 6.
/*
    Gauge(
        pinColor = Color(0xFF333333),
        markColor = Color(0xFF000000),
        backColor = Color(0xFFFFFFFF),
        markLevel = MarkLevel.High,
        minDegree = 0f,
        maxDegree = 120f,
        degree = 1880f,
        fontSize = 8.sp,
        fontColor = Color(0xFF333333),
        modifier = Modifier)
*/

    // 7.
/*
    RingProgress(
        modifier = Modifier.size(100.dp),
        barWidth = 0.3f,
        progressColor = Color(0xFF000000),
        degree = 12f,
        maxDegree = 120f,
        fontSize = 9.sp,
        fontColor = Color(0xFF000000)
    )
*/

    // 8. Modifier - convexBorder
/*
    var text by remember { mutableStateOf("") }
    BasicTextField(
        value = text,
        onValueChange = {
            text = it
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Search
        ),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .size(300.dp, 60.dp)
                    .background(Color(0XFFAAAAAA), CircleShape)
                    .convexBorder(
                        color = Color(0XFFCCCCDD),
                        shape = CircleShape,
                        convexStyle = ConvexStyle(
//                            shadowColor = Color(0xFFDD0000).copy(0.7f)
                        )
                    )
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null
                )
                Box {
                    if (text.isEmpty()) {
                        Text(
                            text = "Search...",
                            style = LocalTextStyle.current.copy(Color(0xFF242424))
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
*/

    // 9. NeuMorphismButton example
/*
    Column(
        modifier = Modifier
            .size(280.dp, 400.dp)
            .background(Color(0xFFAAAAAA))
            .padding(40.dp)
    ) {
        Row {
            NeuMorphismButton(
                modifier = Modifier
                    .size(50.dp, 50.dp),
                cornerRadius = 10.dp,
                backColor = Color(0xFFAAAAAA),
                text= "Btn"
            ) {

            }

            Spacer(modifier = Modifier.width(25.dp))

            NeuMorphismButton(
                modifier = Modifier
                    .size(50.dp, 50.dp),
                cornerRadius = 10.dp,
                backColor = Color(0xFFAAAAAA),
                text= "Btn"
            ) {

            }

            Spacer(modifier = Modifier.width(25.dp))

            NeuMorphismButton(
                modifier = Modifier
                    .size(50.dp, 50.dp),
                cornerRadius = 10.dp,
                backColor = Color(0xFFAAAAAA),
                text= "Btn"
            ) {

            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        NeuMorphismButton(
            modifier = Modifier
                .size(200.dp, 70.dp),
            cornerRadius = 20.dp,
            backColor = Color(0xFFAAAAAA),
            text= "Button1"
        ) {

        }
        Spacer(modifier = Modifier.height(30.dp))

        Row {
            NeuMorphismButton(
                modifier = Modifier
                    .size(90.dp, 45.dp),
                cornerRadius = 1.dp,
                backColor = Color(0xFFAAAAAA),
                text= "Button2"
            ) {

            }

            Spacer(modifier = Modifier.width(20.dp))

            NeuMorphismButton(
                modifier = Modifier
                    .size(90.dp, 45.dp),
                cornerRadius = 1.dp,
                backColor = Color(0xFFAAAAAA),
                text= "Button3"
            ) {

            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row {
            NeuMorphismButton(
                modifier = Modifier
                    .size(50.dp, 50.dp),
                cornerRadius = 25.dp,
                backColor = Color(0xFFAAAAAA),
                text= "Btn"
            ) {

            }

            Spacer(modifier = Modifier.width(25.dp))

            NeuMorphismButton(
                modifier = Modifier
                    .size(50.dp, 50.dp),
                cornerRadius = 25.dp,
                backColor = Color(0xFFAAAAAA),
                text= "Btn"
            ) {

            }

            Spacer(modifier = Modifier.width(25.dp))

            NeuMorphismButton(
                modifier = Modifier
                    .size(50.dp, 50.dp),
                cornerRadius = 25.dp,
                backColor = Color(0xFFAAAAAA),
                text= "Btn"
            ) {

            }
        }
    }
*/

    // 10. Collapsing TopBar
/*
    Surface(modifier = Modifier.fillMaxSize()) {
        val state = rememberCollapsingToolbarScaffoldState()
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
            state = state,
            toolbar = {
                Box(
                    modifier = Modifier
                        .background(Color.Gray)
                        .fillMaxWidth()
                        .height(150.dp)
                        .pin()
                )

                val textSize = (18 + (30 - 18) * state.toolbarState.progress).sp
                Text(
                    text = "Title",
                    modifier = Modifier
                        .padding(10.dp)
                        .road(
                            whenCollapsed = Alignment.CenterStart,
                            whenExpanded = Alignment.BottomEnd
                        )
//                        .alpha(state.toolbarState.progress)
                    ,
                    color = Color.Black,
                    fontSize = textSize
                )

                Image(
                    modifier = Modifier
                        .pin()
                        .padding(16.dp)
                        .alpha(state.toolbarState.progress),
                    painter = painterResource(id = R.drawable.icn_main_cam),
                    contentDescription = ""
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = (40 - 40 * state.toolbarState.progress).dp)
            ) {
                items(20) {
                    Text(
                        "#items $it",
                        modifier = Modifier
                            .padding(12.dp)
                            .background(Color.White)
                            .padding(5.dp)
                    )
                }
            }
        }

        Box(modifier =
            Modifier.alpha(if (state.toolbarState.progress == 0f) 1f else 0f)
        ) {
            Text(
                text = "Title",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.Gray)
                    .padding(10.dp),
                color = Color.Black,
                fontSize = 18.sp
            )
        }
    }
*/

    // 11. Animate in Bottom TapBar
/*
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp), contentAlignment = Alignment.BottomCenter) {
        BottomTabBar(
            backColor = Color.White,
            height = 92.dp,
            cornerRadius = 35.dp,
            menus = arrayListOf(
                BottomTabBarItem(painter = painterResource(id = R.drawable.icn_main_cam), title = "Camera"),
                BottomTabBarItem(painter = painterResource(id = R.drawable.icn_main_home), title = "Home"),
                BottomTabBarItem(painter = painterResource(id = R.drawable.icn_main_search), title = "Search")
            )
        ) { selectedIdx ->
            when (selectedIdx) {
                0 -> {
                    // camera
                }

                1 -> {
                    // main
                }

                2 -> {
                    // search
                }
            }
        }
    }
*/

    // 12. Snackbar Countdown
/*
    Box(Modifier.fillMaxSize()) {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

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
                        }
                        SnackbarResult.ActionPerformed -> {
                            // canceled
//                            Toast.makeText(context, "canceled", Toast.LENGTH_SHORT).show()
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
*/

    // 13. responsive Dashboard layout
/*
    val mode = remember {
        mutableStateOf("Portrait")
    }
    val scope = rememberCoroutineScope()
    val offsetX = remember { Animatable(300f) }
    val offsetY = remember { Animatable(0f) }

    val tiles = remember {
        movableContentOf {//  state tracking
            Tile()
            Spacer(Modifier.size(10.dp))
            Tile(modifier = Modifier.offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(20.dp)
    ) {
            // View 에 Animate Offset 처리, View 상태 유지 & View 이동 시 Animation 효과 제어
            Box {
                tiles()
            }

            // Grid 컬럼 제어. 각 View 가 새로 그려지는 이슈 & col size 계산 필..
    //        AnimatedContent(targetState = if (mode.value == "Landscape") 2 else 1, label = "") { colCnt ->
    //            LazyVerticalGrid(
    //                modifier = Modifier.width(208.dp / colCnt).wrapContentHeight(),
    //                columns = GridCells.Adaptive(100.dp),
    //                verticalArrangement = Arrangement.spacedBy(8.dp),
    //                horizontalArrangement = Arrangement.spacedBy(8.dp)
    //            ) {
    //                items(2) {
    //                    Tile()
    //                }
    //            }
    //        }

            // 컨탠츠 애니메이션 처리. 각 View 가 새로 그려지는 이슈..
    //        AnimatedContent(targetState = mode.value == "Landscape", label = "") { targetState ->
    //            if (targetState) {
    //                Row {
    //                    tiles()
    //                }
    //            } else {
    //                Column {
    //                    tiles()
    //                }
    //            }
    //        }

            // 화면 전환 시 깜빡거리는 듯 즉각 변화. View 상태 유지
    //        if (mode.value == "Landscape") {
    //            Row {
    //                tiles()
    //            }
    //        } else {
    //            Column {
    //                tiles()
    //            }
    //        }

        Spacer(Modifier.height(20.dp))
        AnimatedContent(
            modifier = Modifier.offset { IntOffset(0, offsetY.value.roundToInt()) }
            ,targetState = mode.value, label = "") { targetStr ->
            Text(targetStr)
        }

        Spacer(Modifier.height(20.dp))
        Button(modifier = Modifier
            .offset { IntOffset(0, offsetY.value.roundToInt()) }
            .size(100.dp, 60.dp), onClick = {
            scope.launch {
                    if (mode.value == "Portrait") {
                        mode.value = "Landscape"
                        launch{
                            offsetX.animateTo(0f, animationSpec = tween(400, easing = FastOutSlowInEasing))
                        }
                        launch{
                            offsetY.animateTo(300f, animationSpec = tween(800))
                        }
                    } else {
                        mode.value = "Portrait"
                        launch {
                            offsetX.animateTo(300f, animationSpec = tween(800))
                        }
                        launch {
                            offsetY.animateTo(0f, animationSpec = tween(400, easing = FastOutSlowInEasing))
                        }
                    }
                }
            }
        ) {
            Text("click")
        }
    }
*/

    // 14. Swipe to dismiss
/*
    val emailViewModel = EmailViewModel()
    val messages by emailViewModel.messagesState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 12.dp),
    ) {
        itemsIndexed(
            items = messages,
            // Provide a unique key based on the email content
            key = { _, item -> item.hashCode() }
        ) { _, emailContent ->
            // EmailItem(emailContent, onRemove = emailViewModel::removeItem)
            EmailItem(emailContent) { item ->
                emailViewModel.removeItem(item)
            }
        }
    }
 */

    // 15. Paginated Lazy Column
/*
    var items by remember { mutableStateOf( List(20){ "Item #$it" } ) }// init items list
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    fun loadMoreItems() {
        scope.launch {
            isLoading = true
            delay(1000)

            // load next items list
            val newItems = List(20){ "Item #${items.size + it}" }

            items = items + newItems
            isLoading = false
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Paginated LazyColumn") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    listState.animateScrollToItem(0)
                }
            }) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
        ) {
            PaginatedLazyColumn(
                items = items.toPersistentList(),    //
                // loadMoreItems = { loadMoreItems() },
                loadMoreItems = ::loadMoreItems,
                listState = listState,
                isLoading = isLoading
            )
        }
    }
 */

    // 16. Graphics layers
/*
    Box {
        val pagerState = rememberPagerState (
            initialPage = 0,
            pageCount = { 15 }
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .colorFilter(ColorFilter.colorMatrix(
                    ColorMatrix().apply {
                        setToSaturation(0f)
                    })
                )
        ) { idx ->
            PagerSampleItem(
                page = idx
            )
        }
        Text(
            text = "breaking news",
            fontSize = 22.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
                .blendMode(BlendMode.Difference)
        )
    }
*/

    // 17. Parallax Pager
    ParallaxImagePager(PaddingValues(5.dp))
}
