package com.lemonlightmc.moreplugins.commands.arguments;

import java.util.EnumSet;
import java.util.Objects;

import org.bukkit.Axis;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import com.lemonlightmc.moreplugins.commands.CommandSource;
import com.lemonlightmc.moreplugins.commands.Utils;
import com.lemonlightmc.moreplugins.commands.argumentsbase.Argument;
import com.lemonlightmc.moreplugins.commands.argumentsbase.ArgumentType;
import com.lemonlightmc.moreplugins.commands.argumentsbase.CommandArguments;
import com.lemonlightmc.moreplugins.commands.argumentsbase.LocationType;
import com.lemonlightmc.moreplugins.commands.argumentsbase.StringReader;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandExceptions.DynamicCommandException;
import com.lemonlightmc.moreplugins.commands.exceptions.CommandSyntaxException;
import com.lemonlightmc.moreplugins.exceptions.DynamicExceptionFunction.Dynamic1ExceptionFunktion;
import com.lemonlightmc.moreplugins.math.Location2D;
import com.lemonlightmc.moreplugins.math.MathOperation;
import com.lemonlightmc.moreplugins.math.Rotation;
import com.lemonlightmc.moreplugins.math.ranges.*;

public class NumberArguments {
  private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_VALUE = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      value -> "Invalid Value '" + value + "'");
  private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_RANGE = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      value -> "Invalid Range '" + value + "'");
  private static final DynamicCommandException<Dynamic1ExceptionFunktion> VALUE_TOO_LOW = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      value -> "Value '" + value + "' is too low ");
  private static final DynamicCommandException<Dynamic1ExceptionFunktion> VALUE_TOO_HIGH = new DynamicCommandException<Dynamic1ExceptionFunktion>(
      value -> "Value '" + value + "' is too high ");

  public static class BoolArgument extends Argument<Boolean, BoolArgument> {

    public BoolArgument(final String name) {
      super(name, Boolean.class, ArgumentType.PRIMITIVE_BOOLEAN);
      withSuggestions("true", "false");
    }

    public BoolArgument getInstance() {
      return this;
    }

    @Override
    public Boolean parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      try {
        return reader.readBoolean();
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw e;
      }
    }

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj);
    }

    @Override
    public String toString() {
      return "BoolArgument []";
    }
  }

  public static class IntegerArgument extends Argument<Integer, IntegerArgument> {
    private final IntegerRange range;

    public IntegerArgument(final String name) {
      this(name, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public IntegerArgument(final String name, final int min) {
      this(name, min, Integer.MAX_VALUE);
    }

    public IntegerArgument(final String name, final int min, final int max) {
      super(name, Integer.class, ArgumentType.PRIMITIVE_INTEGER);
      this.range = new IntegerRange(min, max);
    }

    public IntegerArgument getInstance() {
      return this;
    }

    @Override
    public Integer parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      int value = 0;
      try {
        value = reader.readInt();
        if (range.isLower(value)) {
          throw VALUE_TOO_LOW.createWithContext(reader, value, range.getMin());
        }
        if (range.isHigher(value)) {
          throw VALUE_TOO_HIGH.createWithContext(reader, value, range.getMax());
        }
        return value;
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_VALUE.createWithContext(reader, value);
      }
    }

    @Override
    public int hashCode() {
      return 31 * super.hashCode() + range.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      final IntegerArgument other = (IntegerArgument) obj;
      return range.equals(other.range);
    }

    @Override
    public String toString() {
      return "IntegerArgument (" + range.toString() + ")";
    }
  }

  public static class LongArgument extends Argument<Long, LongArgument> {
    private final LongRange range;

    public LongArgument(final String name) {
      this(name, Long.MAX_VALUE, Long.MAX_VALUE);
    }

    public LongArgument(final String name, final long min) {
      this(name, min, Long.MAX_VALUE);
    }

    public LongArgument(final String name, final long min, final long max) {
      super(name, Long.class, ArgumentType.PRIMITIVE_LONG);
      this.range = new LongRange(min, max);
    }

    public LongArgument getInstance() {
      return this;
    }

    @Override
    public Long parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      long value = 0;
      try {
        value = reader.readLong();
        if (range.isLower(value)) {
          throw VALUE_TOO_LOW.createWithContext(reader, value, range.getMin());
        }
        if (range.isHigher(value)) {
          throw VALUE_TOO_HIGH.createWithContext(reader, value, range.getMax());
        }
        return value;
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_VALUE.createWithContext(reader, value);
      }
    }

    @Override
    public int hashCode() {
      return 31 * super.hashCode() + range.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      final LongArgument other = (LongArgument) obj;
      return range.equals(other.range);
    }

    @Override
    public String toString() {
      return "LongArgument (" + range.toString() + ")";
    }
  }

  public static class FloatArgument extends Argument<Float, FloatArgument> {
    private final FloatRange range;

    public FloatArgument(final String name) {
      this(name, Float.MAX_VALUE, Float.MAX_VALUE);
    }

    public FloatArgument(final String name, final float min) {
      this(name, min, Float.MAX_VALUE);
    }

    public FloatArgument(final String name, final float min, final float max) {
      super(name, Float.class, ArgumentType.PRIMITIVE_FLOAT);
      this.range = new FloatRange(min, max);
    }

    public FloatArgument getInstance() {
      return this;
    }

    @Override
    public Float parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      float value = 0;
      try {
        value = reader.readFloat();
        if (range.isLower(value)) {
          throw VALUE_TOO_LOW.createWithContext(reader, value, range.getMin());
        }
        if (range.isHigher(value)) {
          throw VALUE_TOO_HIGH.createWithContext(reader, value, range.getMax());
        }
        return value;
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_VALUE.createWithContext(reader, value);
      }
    }

    @Override
    public int hashCode() {
      return 31 * super.hashCode() + range.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      final FloatArgument other = (FloatArgument) obj;
      return range.equals(other.range);
    }

    @Override
    public String toString() {
      return "FloatArgument (" + range.toString() + ")";
    }
  }

  public static class DoubleArgument extends Argument<Double, DoubleArgument> {
    private final DoubleRange range;

    public DoubleArgument(final String name) {
      this(name, Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public DoubleArgument(final String name, final double min) {
      this(name, min, Double.MAX_VALUE);
    }

    public DoubleArgument(final String name, final double min, final double max) {
      super(name, Double.class, ArgumentType.PRIMITIVE_DOUBLE);
      this.range = new DoubleRange(min, max);
    }

    public DoubleArgument getInstance() {
      return this;
    }

    @Override
    public Double parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      double value = 0;
      try {
        value = reader.readDouble();
        if (range.isLower(value)) {
          throw VALUE_TOO_LOW.createWithContext(reader, value, range.getMin());
        }
        if (range.isHigher(value)) {
          throw VALUE_TOO_HIGH.createWithContext(reader, value, range.getMax());
        }
        return value;
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_VALUE.createWithContext(reader, value);
      }
    }

    @Override
    public int hashCode() {
      return 31 * super.hashCode() + range.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      final DoubleArgument other = (DoubleArgument) obj;
      return range.equals(other.range);
    }

    @Override
    public String toString() {
      return "DoubleArgument (" + range.toString() + ")";
    }
  }

  public static class IntegerRangeArgument extends Argument<IntegerRange, IntegerRangeArgument> {

    public IntegerRangeArgument(final String name) {
      super(name, IntegerRange.class, ArgumentType.RANGE_INT);
    }

    public IntegerRangeArgument getInstance() {
      return this;
    }

    @Override
    public IntegerRange parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readRange();
        return IntegerRange.of(value);
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_RANGE.createWithContext(reader, value);
      }
    }

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj);
    }

    @Override
    public String toString() {
      return "IntegerRangeArgument []";
    }
  }

  public static class LongRangeArgument extends Argument<LongRange, LongRangeArgument> {

    public LongRangeArgument(final String name) {
      super(name, LongRange.class, ArgumentType.RANGE_LONG);
    }

    public LongRangeArgument getInstance() {
      return this;
    }

    @Override
    public LongRange parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readRange();
        return LongRange.of(value);
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_RANGE.createWithContext(reader, value);
      }
    }

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj);
    }

    @Override
    public String toString() {
      return "LongRangeArgument []";
    }
  }

  public static class FloatRangeArgument extends Argument<FloatRange, FloatRangeArgument> {

    public FloatRangeArgument(final String name) {
      super(name, FloatRange.class, ArgumentType.RANGE_FLOAT);
    }

    public FloatRangeArgument getInstance() {
      return this;
    }

    @Override
    public FloatRange parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readRange();
        return FloatRange.of(value);
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_RANGE.createWithContext(reader, value);
      }
    }

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj);
    }

    @Override
    public String toString() {
      return "FloatRangeArgument []";
    }
  }

  public static class DoubleRangeArgument extends Argument<DoubleRange, DoubleRangeArgument> {

    public DoubleRangeArgument(final String name) {
      super(name, DoubleRange.class, ArgumentType.RANGE_DOUBLE);
    }

    public DoubleRangeArgument getInstance() {
      return this;
    }

    @Override
    public DoubleRange parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readRange();
        return DoubleRange.of(value);
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_RANGE.createWithContext(reader, value);
      }
    }

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj);
    }

    @Override
    public String toString() {
      return "DoubleRangeArgument []";
    }
  }

  public static class LocationArgument extends Argument<Location, LocationArgument> {

    public static final int MAX_COORDINATE = 30_000_000;
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_LOCATION = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Location '" + value + "'");
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> OUTOFBOUNDS_LOCATION = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Location is out of bounds'" + value + "' (max " + MAX_COORDINATE + ")");

    private final LocationType type;
    private final boolean centered;

    public LocationArgument(final String nodeName) {
      this(nodeName, LocationType.BLOCK_POSITION);
    }

    public LocationArgument(final String nodeName, final LocationType type) {
      this(nodeName, type, true);
    }

    public LocationArgument(final String nodeName, final LocationType type, final boolean centerPosition) {
      super(nodeName, Location.class, ArgumentType.LOCATION);
      this.type = type;
      this.centered = centerPosition;
      withSuggestions("~", "~ ~", "~ ~ ~");
    }

    @Override
    public LocationArgument getInstance() {
      return this;
    }

    public LocationType getLocationType() {
      return type;
    }

    public boolean getCentered() {
      return centered;
    }

    private double centerPos(final double pos, final boolean center) {
      return type == LocationType.BLOCK_POSITION ? Math.floor(pos)
          : center && Math.floor(pos) == pos ? pos + 0.5d : pos;
    }

    @Override
    public Location parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      double x = 0;
      double y = 0;
      double z = 0;
      try {
        x = centerPos(reader.readDouble(), centered);
        y = centerPos(reader.readDouble(), false);
        z = centerPos(reader.readDouble(), centered);
        if (x > MAX_COORDINATE || x < -MAX_COORDINATE) {
          throw OUTOFBOUNDS_LOCATION.createWithContext(reader, x);
        }
        if (y > MAX_COORDINATE || y < -MAX_COORDINATE) {
          throw OUTOFBOUNDS_LOCATION.createWithContext(reader, y);
        }

        if (z > MAX_COORDINATE || z < -MAX_COORDINATE) {
          throw OUTOFBOUNDS_LOCATION.createWithContext(reader, z);
        }
        return new Location(source.world(), x, y, z);
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_LOCATION.createWithContext(reader, x + "," + y + "," + z);
      }
    }

    @Override
    public int hashCode() {
      final int result = 31 * super.hashCode() + ((type == null) ? 0 : type.hashCode());
      return 31 * result + (centered ? 1231 : 1237);
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      final LocationArgument other = (LocationArgument) obj;
      return type == other.type && centered == other.centered;
    }

    @Override
    public String toString() {
      return "LocationArgument [type=" + type + ", centered=" + centered + "]";
    }
  }

  public static class Location2DArgument extends Argument<Location2D, Location2DArgument> {

    public static final int MAX_COORDINATE = 30_000_000;
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_LOCATION = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Location '" + value + "'");
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> OUTOFBOUNDS_LOCATION = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Location is out of bounds'" + value + "' (max " + MAX_COORDINATE + ")");
    private final LocationType type;
    private final boolean centered;

    public Location2DArgument(final String nodeName) {
      this(nodeName, LocationType.BLOCK_POSITION);
    }

    public Location2DArgument(final String nodeName, final LocationType type) {
      this(nodeName, type, true);
    }

    public Location2DArgument(final String nodeName, final LocationType type, final boolean centerPosition) {
      super(nodeName, Location2D.class, ArgumentType.LOCATION);
      this.type = type;
      this.centered = centerPosition;
      withSuggestions("~", "~ ~");
    }

    @Override
    public Location2DArgument getInstance() {
      return this;
    }

    public LocationType getLocationType() {
      return type;
    }

    public boolean getCentered() {
      return centered;
    }

    private double centerPos(final double pos) {
      return type == LocationType.BLOCK_POSITION ? Math.floor(pos)
          : centered && Math.floor(pos) == pos ? pos + 0.5d : pos;
    }

    @Override
    public Location2D parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      double x = 0;
      double z = 0;
      try {
        x = centerPos(reader.readDouble());
        z = centerPos(reader.readDouble());
        if (x > MAX_COORDINATE || x < -MAX_COORDINATE) {
          throw OUTOFBOUNDS_LOCATION.createWithContext(reader, x);
        }
        if (z > MAX_COORDINATE || z < -MAX_COORDINATE) {
          throw OUTOFBOUNDS_LOCATION.createWithContext(reader, z);
        }
        return new Location2D(source.world(), x, z);
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_LOCATION.createWithContext(reader, x + "," + z);
      }
    }

    @Override
    public int hashCode() {
      final int result = 31 * super.hashCode() + ((type == null) ? 0 : type.hashCode());
      return 31 * result + (centered ? 1231 : 1237);
    }

    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (!super.equals(obj) || getClass() != obj.getClass()) {
        return false;
      }
      final LocationArgument other = (LocationArgument) obj;
      return type == other.type && centered == other.centered;
    }

    @Override
    public String toString() {
      return "Location2DArgument [type=" + type + ", centered=" + centered + "]";
    }
  }

  public static class AxisArgument extends Argument<Axis, AxisArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_AXIS = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Axis '" + value + "'");

    public AxisArgument(final String name) {
      super(name, Axis.class, ArgumentType.AXIS);
      withSuggestions("x", "y", "z");
    }

    public AxisArgument getInstance() {
      return this;
    }

    @Override
    public Axis parseArgument(final CommandSource<CommandSender> source, final StringReader reader, final String key,
        final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(Axis.valueOf(value));
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_AXIS.createWithContext(reader, value);
      }
    }

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj);
    }

    @Override
    public String toString() {
      return "AxisArgument []";
    }
  }

  @SuppressWarnings("rawtypes")
  public static class MultiAxisArgument extends Argument<EnumSet, MultiAxisArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_AXIS = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Axis '" + value + "'");

    public MultiAxisArgument(final String name) {
      super(name, EnumSet.class, ArgumentType.AXIS);
      withSuggestions("x", "xy", "xyz", "xzy", "xz", "y", "yx", "yxz", "yzx", "yz", "z", "zx", "zxy", "zyx", "zy");
    }

    public MultiAxisArgument getInstance() {
      return this;
    }

    @Override
    public EnumSet<Axis> parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readString();
        final EnumSet<Axis> set = EnumSet.noneOf(Axis.class);
        for (int i = 0; i < value.length(); i++) {
          set.add(Objects.requireNonNull(Axis.valueOf(value.substring(i, i + 1))));
        }
        return set;
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_AXIS.createWithContext(reader, value);
      }

    }

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj);
    }

    @Override
    public String toString() {
      return "MultiAxisArgument []";
    }
  }

  public static class RotationArgument extends Argument<Rotation, RotationArgument> {
    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_ROTATION = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid Rotation '" + value + "'");

    public RotationArgument(final String name) {
      super(name, Rotation.class, ArgumentType.ROTATION);
      withSuggestions("0", "90", "180", "270", "-90", "-180", "-270");
    }

    public RotationArgument getInstance() {
      return this;
    }

    @Override
    public Rotation parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      double yaw = 0;
      double pitch = 0;
      try {
        yaw = reader.readDouble();
        pitch = reader.readDouble();
        return new Rotation(yaw, pitch);
      } catch (final CommandSyntaxException e) {
        reader.resetCursor();
        throw e;
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_ROTATION.createWithContext(reader, yaw + "," + pitch);
      }
    }

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj);
    }

    @Override
    public String toString() {
      return "RotationArgument []";
    }
  }

  public static class MathOperationArgument extends Argument<MathOperation, MathOperationArgument> {
    public static final String[] NAMES = Utils.mapRegistry(MathOperation.values());

    private static final DynamicCommandException<Dynamic1ExceptionFunktion> INVALID_OPERATION = new DynamicCommandException<Dynamic1ExceptionFunktion>(
        value -> "Invalid MathOperation '" + value + "'");

    public MathOperationArgument(final String name) {
      super(name, MathOperation.class, ArgumentType.MATH_OPERATION);
      withSuggestions("true", "false");
    }

    public MathOperationArgument getInstance() {
      return this;
    }

    @Override
    public MathOperation parseArgument(final CommandSource<CommandSender> source, final StringReader reader,
        final String key, final CommandArguments previousArgs)
        throws CommandSyntaxException {
      reader.point();
      String value = null;
      try {
        value = reader.readString();
        return Objects.requireNonNull(MathOperation.fromString(value));
      } catch (final Exception e) {
        reader.resetCursor();
        throw INVALID_OPERATION.createWithContext(reader, value);
      }
    }

    @Override
    public boolean equals(final Object obj) {
      return this == obj && getClass() == obj.getClass() && super.equals(obj);
    }

    @Override
    public String toString() {
      return "MathOperationArgument []";
    }
  }
}
