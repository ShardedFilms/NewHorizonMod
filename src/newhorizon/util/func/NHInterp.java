package newhorizon.util.func;

import arc.math.Interp;

public class NHInterp{
	public static final Interp artillery = x -> 1 - 2 * (x-0.5f) * (x-0.5f);
	public static final Interp.BounceOut bounce5Out = new Interp.BounceOut(5);
	public static final Interp.Pow pow10 = new Interp.Pow(10);
	public static final Interp zero = a -> 0;
	public static final Interp inOut = a -> 2 * (0.9f * a + 0.31f) + 1f / (5f * (a + 0.1f)) - 1.6f;
	public static final Interp inOut2 = x -> 1.6243f * (0.9f * x + 0.46f) + 1 / (10 * (x + 0.1f)) -1.3f;
	public static final Interp parabola4 = x -> 4 * (x - 0.5f) * (x - 0.5f);
	public static final Interp parabola4Reversed = x -> -4 * (x - 0.5f) * (x - 0.5f) + 1;
	public static final Interp parabola4ReversedOver = x -> (-4 * (x - 0.5f) * (x - 0.5f) + 1) * 2.75f;
}
