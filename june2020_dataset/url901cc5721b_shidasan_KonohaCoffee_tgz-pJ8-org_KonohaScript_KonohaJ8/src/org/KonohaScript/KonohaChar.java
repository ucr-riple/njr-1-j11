/****************************************************************************
 * Copyright (c) 2012, the Konoha project authors. All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ***************************************************************************/

package org.KonohaScript;



public abstract class KonohaChar {
	public final static int Null                 =  0;
	public final static int Undefined            =  1;
	public final static int Digit                =  2;
	public final static int UpperAlpha           =  3;
	public final static int LowerAlpha           =  4;
	public final static int Unicode              =  5;
	public final static int NewLine              =  6;
	public final static int Tab                  =  7;
	public final static int Space                =  8;
	public final static int OpenParenthesis      =  9;
	public final static int CloseParenthesis     = 10;
	public final static int OpenBracket          = 11;
	public final static int CloseBracket         = 12;
	public final static int OpenBrace            = 13;
	public final static int CloseBrace           = 14;
	public final static int LessThan             = 15;
	public final static int GreaterThan          = 16;
	public final static int Quote                = 17;
	public final static int DoubleQuote          = 18;
	public final static int BackQuote            = 19;
	public final static int Surprised            = 20;
	public final static int Sharp                = 21;
	public final static int Dollar               = 22;
	public final static int Percent              = 23;
	public final static int And                  = 24;
	public final static int Star                 = 25;
	public final static int Plus                 = 26;
	public final static int Comma                = 27;
	public final static int Minus                = 28;
	public final static int Dot                  = 29;
	public final static int Slash                = 30;
	public final static int Colon                = 31;
	public final static int SemiColon            = 32;
	public final static int Equal                = 33;
	public final static int Question             = 34;
	public final static int AtMark               = 35;
	public final static int Var                  = 36;
	public final static int Childer              = 37;
	public final static int BackSlash            = 38;
	public final static int Hat                  = 39;
	public final static int UnderBar             = 40;
	public final static int MAX                  = 41;

	static final int cMatrix[] = {
		0/*nul*/, 1/*soh*/, 1/*stx*/, 1/*etx*/, 1/*eot*/, 1/*enq*/, 1/*ack*/, 1/*bel*/,
		1/*bs*/,  Tab/*ht*/, NewLine/*nl*/, 1/*vt*/, 1/*np*/, 1/*cr*/, 1/*so*/, 1/*si*/,
		/*020 dle  021 dc1  022 dc2  023 dc3  024 dc4  025 nak  026 syn  027 etb */
		1, 1, 1, 1,     1, 1, 1, 1,
		/*030 can  031 em   032 sub  033 esc  034 fs   035 gs   036 rs   037 us */
		1, 1, 1, 1,     1, 1, 1, 1,
		/*040 sp   041  !   042  "   043  #   044  $   045  %   046  &   047  ' */
		Space, Surprised, DoubleQuote, Sharp, Dollar, Percent, And, Quote,
		/*050  (   051  )   052  *   053  +   054  ,   055  -   056  .   057  / */
		OpenParenthesis, CloseParenthesis, Star, Plus, Comma, Minus, Dot, Slash,
		/*060  0   061  1   062  2   063  3   064  4   065  5   066  6   067  7 */
		Digit, Digit, Digit, Digit,  Digit, Digit, Digit, Digit,
		/*070  8   071  9   072  :   073  ;   074  <   075  =   076  >   077  ? */
		Digit, Digit, Colon, SemiColon, LessThan, Equal, GreaterThan, Question,
		/*100  @   101  A   102  B   103  C   104  D   105  E   106  F   107  G */
		AtMark, UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha,
		/*110  H   111  I   112  J   113  K   114  L   115  M   116  N   117  O */
		UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha,
		/*120  P   121  Q   122  R   123  S   124  T   125  U   126  V   127  W */
		UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha, UpperAlpha,
		/*130  X   131  Y   132  Z   133  [   134  \   135  ]   136  ^   137  _ */
		UpperAlpha, UpperAlpha, UpperAlpha, OpenBracket, BackSlash, CloseBracket, Hat, UnderBar,
		/*140  `   141  a   142  b   143  c   144  d   145  e   146  f   147  g */
		BackQuote, LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha,
		/*150  h   151  i   152  j   153  k   154  l   155  m   156  n   157  o */
		LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha,
		/*160  p   161  q   162  r   163  s   164  t   165  u   166  v   167  w */
		LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha, LowerAlpha,
		/*170  x   171  y   172  z   173  {   174  |   175  }   176  ~   177 del*/
		LowerAlpha, LowerAlpha, LowerAlpha, OpenBrace, Var, CloseBrace, Childer, 1,
	};
	
	static int JavaCharToKonohaChar(char c) {
		if(c < 128) {
			return cMatrix[(int)c];
		}
		return Unicode;
	}
}
